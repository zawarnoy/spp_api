package spp.lab.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spp.lab.models.Payment;
import spp.lab.models.Subscription;
import spp.lab.models.User;
import spp.lab.models.UserSubscription;
import spp.lab.reposository.BaseRepository;
import spp.lab.service.PaymentService;
import spp.lab.service.UserSubscriptionService;

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
        if(paymentService.checkExistence(Long.valueOf(id))) {
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

//    @Transactional()
//    @RequestMapping(method = RequestMethod.POST, path = "/{id}")
//    public @ResponseBody
//    String edit(@PathVariable(value = "id") String id,
//                @RequestParam String subscription_id,
//                @RequestParam String user_id) {
//
//        Optional<Payment> payment = paymentRepository.findById(Long.valueOf(id));
//
//        if (payment.isPresent()) {
//
//            if (subscription_id != null) {
//                payment.get().setSubscription(subscriptionRepository.findById(Long.valueOf(subscription_id)).get());
//                payment.get().setPrice(subscriptionRepository.findById(Long.valueOf(subscription_id)).get().getPrice());
//            }
//
//            if (user_id != null)
//                payment.get().setUser(userRepository.findById(Long.valueOf(user_id)).get());
//            paymentRepository.save(payment.get());
//            return "{ status : success }";
//        }
//
//        return "{ status : error }";
//    }

    @RequestMapping("/{id}")
    public @ResponseBody
    Optional<Payment> show(@PathVariable(value = "id") String id) {
        return paymentService.findByStringId(id);
    }


}
