package spp.lab.service.documentsGeneration;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import spp.lab.models.Payment;
import spp.lab.models.State;
import spp.lab.reposository.PaymentRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PdfDocumentsGenerationService extends BaseDocumentsGenerationService<Document> {

    @Autowired
    PaymentRepository paymentRepository;

    private final String repositoryPath = "\\docs\\pdf\\";

    private void generateRevenue(String pathToDocument, ArrayList<Payment> payments, String
            caption) throws Exception {


        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(pathToDocument));
        document.open();

        PdfPTable table = new PdfPTable(3);


        PdfPCell cell = new PdfPCell(new Paragraph(caption));

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingBottom(5);
        cell.setColspan(3);

        table.addCell(cell);

        PdfPCell contentsCell = new PdfPCell();


        table.addCell("Name");
        table.addCell("Date");
        table.addCell("Payment");

        int totalRevenue = 0;

        for (int i = 0; i < payments.size(); i++) {
            table.addCell(new Paragraph(payments.get(i).getUser().getUsername()));
            table.addCell(new Paragraph(payments.get(i).getCreated_at().toString()));
            table.addCell(new Paragraph(payments.get(i).getPrice().toString()));
            totalRevenue += payments.get(i).getPrice();
        }

        cell = new PdfPCell(new Paragraph("Total revenue:"));
        cell.setColspan(2);
        cell.setPaddingTop(10);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(String.valueOf(totalRevenue)));
        cell.setPaddingTop(10);
        table.addCell(cell);

        document.add(table);
        document.close();

    }

    private String generatePdfName(String pathToFolder, String caption) {
        return this.generateFilename(pathToFolder, repositoryPath, caption) + ".pdf";
        }

    public String generateMonthlyRevenue(String pathToFolder, Iterable<Payment> payments) throws Exception {
        String fileName = this.generatePdfName(pathToFolder, "Monthly");
        this.generateRevenue(fileName, this.getMonthlyPayments(payments), "Monthly revenue");
        return fileName;
    }

    public String generateYearlyRevenue(String pathToFolder, Iterable<Payment> payments) throws Exception {
        String fileName = this.generatePdfName(pathToFolder, "Yearly");
        this.generateRevenue(fileName, this.getYearlyPayments(payments), "Yearly revenue");
        return fileName;
    }


    public String generateUserPayment(String pathToFolder, Optional<Payment> optionalPayment) throws Exception
    {
        Payment payment = optionalPayment.get();

        String fileName = this.generatePdfName(pathToFolder, payment.getUser().getUsername() + "Payment");

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();

        PdfPTable table = new PdfPTable(2);


        PdfPCell cell = new PdfPCell(new Paragraph(payment.getUser().getUsername() + " Payment"));

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingBottom(10);
        cell.setColspan(2);
        table.addCell(cell);


        SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

        table.addCell(new Paragraph("Date"));
        table.addCell(new Paragraph(dt.format(payment.getCreated_at())));


        table.addCell(new Paragraph("Subscription"));
        table.addCell(new Paragraph(payment.getSubscription().getName()));

        table.addCell(new Paragraph("Added visits"));
        table.addCell(new Paragraph(payment.getSubscription().getVisitCount().toString()));

        table.addCell(new Paragraph("Duration"));
        table.addCell(new Paragraph(payment.getSubscription().getDuration().toString()));

        table.addCell(new Paragraph("Payment"));
        table.addCell(new Paragraph(payment.getPrice().toString()));


        document.add(table);
        document.close();

        return fileName;
    }



}
