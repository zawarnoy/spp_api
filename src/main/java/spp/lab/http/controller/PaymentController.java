package spp.lab.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spp.lab.models.Payment;
import spp.lab.models.Subscription;
import spp.lab.models.User;
import spp.lab.models.UserSubscription;
import spp.lab.reposository.BaseRepository;
import spp.lab.service.PaymentService;
import spp.lab.service.UserSubscriptionService;
import spp.lab.service.documentsGeneration.CsvDocumentsGenerationService;
import spp.lab.service.documentsGeneration.PdfDocumentsGenerationService;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "/payments")
public class PaymentController {

    @Autowired
    private BaseRepository<Payment, Long> paymentRepository;

    @Autowired
    private BaseRepository<User, Long> userRepository;

    @Autowired
    private BaseRepository<Subscription, Long> subscriptionRepository;

    @Autowired
    private UserSubscriptionService userSubscriptionService;

    @Autowired
    private PaymentService paymentService;

    private PdfDocumentsGenerationService pdfDocumentsGenerationService = new PdfDocumentsGenerationService();

    private CsvDocumentsGenerationService csvDocumentsGenerationService = new CsvDocumentsGenerationService();

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    String add(
            @RequestParam String subscription_id,
            @RequestParam String user_id) {

        Subscription subscription = subscriptionRepository.findById(Long.valueOf(subscription_id)).get();
        User user = userRepository.findById(Long.valueOf(user_id)).get();

        paymentService.create(subscription, user);

        Date endDate = paymentService.addDaysToCurrentDate(subscription.getDuration());

        Date endDateToExistence;

        Optional<UserSubscription> userSubscription = userSubscriptionService.findFirstByUser(user);

        if (userSubscription.isPresent()) {
            endDateToExistence = paymentService.addDaysToGrantedDate(userSubscription.get().getEnd_date(), subscription.getDuration());
            userSubscriptionService.edit(userSubscription.get(), subscription.getVisitCount(), endDateToExistence);
        } else {
            userSubscriptionService.create(user, subscription.getVisitCount(), endDate);
        }
        return "{ status : success }";
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody
    String delete(@PathVariable(value = "id") String id) {
        Optional<Payment> payment = paymentRepository.findById(Long.valueOf(id));
        if (paymentService.checkExistence(Long.valueOf(id))) {
            paymentService.delete(payment.get());
            return "{ status : success }";
        } else {
            return "{ status : error, value : can't find payment }";
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @RequestMapping("/{id}")
    Optional<Payment> show(@PathVariable(value = "id") String id) {
        return paymentService.findByStringId(id);
    }


    @GetMapping(path = "/monthly.pdf")
    public ResponseEntity<byte[]> getPdfMonthly() {
        String project_path = System.getProperty("user.dir");
        byte[] data = null;

        String fileName = null;

        try {
            fileName = pdfDocumentsGenerationService.generateMonthlyRevenue(project_path, paymentRepository.findAll());
            data = pdfDocumentsGenerationService.getBytesFromFilename(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Date date = new Date();
        String webFileName = String.valueOf(date.getTime());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setContentDispositionFormData("file", "Monthly" + webFileName + ".pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);

    }

    @GetMapping(path = "/yearly.pdf")
    public ResponseEntity<byte[]> getPdfYearly() {
        String project_path = System.getProperty("user.dir");
        byte[] data = null;

        String fileName = null;

        try {
            fileName = pdfDocumentsGenerationService.generateYearlyRevenue(project_path, paymentRepository.findAll());
            data = pdfDocumentsGenerationService.getBytesFromFilename(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Date date = new Date();
        String webFileName = String.valueOf(date.getTime());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setContentDispositionFormData("file", "Yearly" + webFileName + ".pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);

    }


    @GetMapping(path = "/{id}.pdf")
    public ResponseEntity<byte[]> getPaymentPdf(@PathVariable(value = "id") String id) {
        String project_path = System.getProperty("user.dir");
        byte[] data = null;

        String fileName = null;

        try {
            fileName = pdfDocumentsGenerationService.generateUserPayment(project_path, paymentService.findByStringId(id));
            data = pdfDocumentsGenerationService.getBytesFromFilename(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Date date = new Date();
        String webFileName = String.valueOf(date.getTime());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setContentDispositionFormData("file", "UserPayment" + webFileName + ".pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
    }


    @GetMapping(path = "/{id}.csv")
    public ResponseEntity<byte[]> getCsvPayment(@PathVariable(value = "id") String id) {
        String project_path = System.getProperty("user.dir");

        Date date = new Date();
        String webFileName = String.valueOf(date.getTime());

        Optional<Payment> payment = paymentService.findByStringId(id);
        String path = null;

        byte[] data = null;
        String fileName = null;
        

        try {
            fileName = csvDocumentsGenerationService.generatePaymentCsv(project_path, payment.get());
            data = pdfDocumentsGenerationService.getBytesFromFilename(fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/csv"));
        headers.setContentDispositionFormData("file", "AllAcivePayments" + webFileName + ".csv");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
    }


    @GetMapping(path = ".csv")
    public ResponseEntity<byte[]> getCsvPayments(@PathVariable(value = "id") String id) {
        String project_path = System.getProperty("user.dir");

        byte[] data = null;
        String fileName = null;

        Date date = new Date();
        String webFileName = String.valueOf(date.getTime());

        Iterable<Payment> payments = paymentService.findAllActive();
        String path = null;
        try {
            fileName = csvDocumentsGenerationService.generatePaymentsCsv(project_path, payments);
            data = pdfDocumentsGenerationService.getBytesFromFilename(fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/csv"));
        headers.setContentDispositionFormData("file", "AllAcivePayments" + webFileName + ".csv");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
    }

}
