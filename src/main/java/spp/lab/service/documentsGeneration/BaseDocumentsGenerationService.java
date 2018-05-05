package spp.lab.service.documentsGeneration;

import spp.lab.models.Payment;

import java.util.*;

public abstract class BaseDocumentsGenerationService<T> implements DocumentsGeneration<T> {

    protected List<Payment> getMonthlyPayments(Iterable<Payment> payments) {
        List<Payment> data = new ArrayList<>();
        for (Payment payment :
                payments) {
            if (checkNecessityOfAddingPayment(1, payment.getCreated_at()))
                data.add(payment);
        }
        return data;
    }

    protected List<Payment> getYearlyPayments(Iterable<Payment> payments) {
        List<Payment> data = new ArrayList<>();
        for (Payment payment :
                payments) {
            if (checkNecessityOfAddingPayment(12, payment.getCreated_at()))
                data.add(payment);
        }
        return data;
    }

    private boolean checkNecessityOfAddingPayment(int monthsBefore, Date paymentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -monthsBefore);

        return calendar.before(paymentDate);
    }


}
