package org.eventmanagmentsystem.services;

import java.util.Scanner;

public class testPricing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank bank=new Bank();
        PricingService customer;
        System.out.println("Enter the card details: ");
        bank.setCard_no(sc.next());
        bank.setCVV(sc.next());
        bank.setMonth(sc.next());
        bank.setYear(sc.next());


        System.out.println("Enter the hall you want to book: ");
        PricingService hall=new PricingService(sc.nextInt(),bank);
        System.out.println("The total equals: "+hall.getTotal());




    }
}
