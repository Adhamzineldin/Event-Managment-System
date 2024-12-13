package org.eventmanagmentsystem.services;

public class Receet {
    private double total;
    private double subtax;
    private double ServicePrice;
    public Receet(PricingService pricingService){
        total = pricingService.getTotal();
        subtax= pricingService.getTaxRate();
    }
}
