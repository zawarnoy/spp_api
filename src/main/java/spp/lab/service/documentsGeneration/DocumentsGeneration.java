package spp.lab.service.documentsGeneration;

import spp.lab.models.Payment;

public interface DocumentsGeneration<T> {

    public T generateMonthlyRevenue(T document, Iterable<Payment> payments) throws Exception;

    public void generateYearlyRevenue(T document, Iterable<Payment> payments) throws Exception;

    public void generateTopTenTrainersInLastMonth(T document) throws Exception;

    public void generateTopTenTrainersInYear(T document) throws Exception;

    public void generateTopTenUsers(T document) throws Exception;


}
