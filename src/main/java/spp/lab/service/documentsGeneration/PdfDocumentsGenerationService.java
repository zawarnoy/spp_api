package spp.lab.service.documentsGeneration;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import spp.lab.models.Payment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PdfDocumentsGenerationService extends BaseDocumentsGenerationService<Document> {


    @Override
    public Document addMonthlyRevenue(Document document, Iterable<Payment> payments) throws Exception {
        List<Payment> actualPayments = this.getMonthlyPayments(payments);

        document.open();
        PdfPTable table = new PdfPTable(3);
        PdfPTable row = new PdfPTable(3);

        row.addCell("Name");
        row.addCell("Date");
        row.addCell("Price");
        table.addCell(row);

//        for (Payment payment:
//             payments) {
//            PdfPCell cell = new PdfPCell();
//        }

        document.add(table);

        return document;

    }

    @Override
    public void addYearlyRevenue(Document document, Iterable<Payment> payments) throws Exception {

    }

    @Override
    public void addTopTenTrainersInLastMonth(Document document) throws Exception {

    }

    @Override
    public void addTopTenTrainersInYear(Document document) throws Exception {

    }

    @Override
    public void addTopTenUsers(Document document) throws Exception {

    }

    public Document create(String filename) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        return document;
    }



}
