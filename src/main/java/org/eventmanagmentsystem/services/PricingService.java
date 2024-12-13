package org.eventmanagmentsystem.services;

import java.math.BigInteger;
import java.util.Date;

public class PricingService {
    /* new class Bank
    that contains the details about the card
    which will pay the total of the event booking
      in Bank class file*/
    Bank customer;
    private double  total;
    private final float taxRate=0.15f;
    private int choice;
    private double choice_result;
    private double serivce_result;
    private double getResult(int choice){
        switch(choice){
            case 1:
                choice_result=900;
                break;
            case 2:
                choice_result=750;
                break;
            case 3:
                choice_result=450;
                break;
            case 4:
                choice_result=250;
                break;
            default:
                choice_result=0;
                break;
        }
        return choice_result;
    }
    public PricingService(int choice,Bank customer) {
        this.choice=choice;
        this.customer=customer;
        choice_result=getResult(choice);
        if(choice_result==0){
            throw new IllegalArgumentException("Invalid type");
        }
        else{
            total=choice_result+choice_result*taxRate;
        }
    }
    public PricingService(){

    }
    public double getChoice_result(){
        return choice_result;
    }
    public double getTotal() {
        if(total==0){
            throw new IllegalArgumentException("INVALID TOTAL");
        }
        else{
            return total;
        }

    }
    public double getTaxRate() {
        return taxRate;
    }
//    public String sendEmails(){}/*this stands for notifications and emails and we want the file
//    that submits the success of the payment and the email with string type of the customer and email to the manager*/
}
