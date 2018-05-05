package spp.lab.service.documentsGeneration;

import spp.lab.models.Payment;

public interface DocumentsGeneration<T> {

    public T addMonthlyRevenue(T document, Iterable<Payment> payments) throws Exception;

    public void addYearlyRevenue(T document, Iterable<Payment> payments) throws Exception;

    public void addTopTenTrainersInLastMonth(T document) throws Exception;

    public void addTopTenTrainersInYear(T document) throws Exception;

    public void addTopTenUsers(T document) throws Exception;

    public T create(String name) throws Exception;

}
