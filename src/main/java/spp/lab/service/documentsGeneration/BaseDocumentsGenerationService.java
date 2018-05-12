package spp.lab.service.documentsGeneration;

import spp.lab.models.Payment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class BaseDocumentsGenerationService<T>  {

    protected ArrayList<Payment> getMonthlyPayments(Iterable<Payment> payments) {
        ArrayList<Payment> data = new ArrayList<>();
        for (Payment payment :
                payments) {
            if (checkNecessityOfAddingPayment(1, payment.getCreated_at()))
                data.add(payment);
        }
        return data;
    }

    protected ArrayList<Payment> getYearlyPayments(Iterable<Payment> payments) {
        ArrayList<Payment> data = new ArrayList<>();
        for (Payment payment :
                payments) {
            if (checkNecessityOfAddingPayment(12, payment.getCreated_at()))
                data.add(payment);
        }
        return data;
    }

    private boolean checkNecessityOfAddingPayment(int monthsBefore, Date paymentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, monthsBefore);
        return paymentDate.compareTo(calendar.getTime()) < 0;
    }

    public byte[] getBytesFromFilename(String pathToFile) throws IOException {
        byte[] data = null;
        Path path = Paths.get(pathToFile);
        data = Files.readAllBytes(path);

        return data;
    }

    protected String generateFilename(String pathToFolder, String repositoryPath,String caption) {
        Date date = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("yyyymmddhhmmss");
        return pathToFolder + repositoryPath + caption + dt.format(date);
    }

}
