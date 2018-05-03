package spp.lab.service.documentsGeneration;

import org.springframework.core.io.Resource;

public interface DocumentsGeneration {

    public Resource generateMonthlyRevenue();

    public Resource generateYearlyRevenue();

    public Resource generateTopTenTrainersInLastMonth();

    public Resource generateTopTenTrainersInYear();

    public Resource generateTopTenSubscriptions();

}
