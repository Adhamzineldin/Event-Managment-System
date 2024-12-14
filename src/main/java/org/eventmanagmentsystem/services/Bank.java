package org.eventmanagmentsystem.services;
public class Bank {
    private String card_no;
    private String CVV;
    private String year;
    private String month;
    private final java.util.Date date= new java.util.Date();
    public Bank(String card, String cvv, String year,String month) {
        this.card_no = card;
        if(!checkCard(card)) {
            throw new IllegalArgumentException("Card number is not valid");
        }
        this.CVV=cvv;
        if(!checkCVV(cvv)){
            throw new IllegalArgumentException("Invalid CVV");
        }
        if(checkMonth(month)){
            this.month=month;
        }
        else{
            throw new IllegalArgumentException("Invalid month");
        }
           if(!checkYear(year)){
               throw new IllegalArgumentException("Invalid year");
           }
           else{
               this.year=year;
           }
        }
        public Bank(){

        }
        /*for the check of the card number*/



    /*emptyCard is a function
    that tells us that the card_no is null,
    same as it in CVV also
     */
    private boolean emptyCard(String card_no){
        return (card_no == null);
    }
    private boolean emptyCVV(String cvv){
        return cvv == null;
    }
    private boolean yearEmpty(String year){
        return (year == null);
    }
    private boolean monthEmpty(String month){
        return (month ==null);
    }
    public boolean checkYear(String year){
        if(emptyCard(year)){
            throw new IllegalArgumentException("This field is required ");
        }
        else if(year.length()!=2){
            throw new IllegalArgumentException("This field should be 2 digits");
        }
        else if(!(year.charAt(0)>='0' && year.charAt(0)<='9' && year.charAt(1)>='0' && year.charAt(1)<='9')){
            throw new IllegalArgumentException("This field should be digits");
        }
        else {
            return true;
        }
    }
    public boolean NotexpireDate(String Month,String Year){
        boolean flag=false;
        int month_int=Integer.parseInt(Month);
        int current_month=(date.getMonth()%12)+1;
        int current_year = date.getYear()%100;
        int yearInt = Integer.parseInt(Year);
        if(yearInt<current_year){
            throw new IllegalArgumentException("Expired");
        }
        else if(yearInt==current_year){
            if(current_month==month_int){
                throw new IllegalArgumentException("Expired");
            }
           else if(current_month>month_int){
               throw new IllegalArgumentException("Expired");
            }
           else{
               flag=true;
            }

        }
        else{
            flag=true;
        }
        return flag;



    }
    public boolean checkMonth(String month) {
        boolean check = false;
        if (emptyCard(month)) {//in case the month is kept empty
            throw new IllegalArgumentException("This field is required ");
        } else if (month.length() == 2) {
            if (month.charAt(0) >= '0' && month.charAt(0) <= '1') {
                if (month.charAt(0) == '0' && (month.charAt(1) >= '1') && (month.charAt(1) <= '9')) {
                    check=true;
                } else if (month.charAt(0) == '1' && (month.charAt(1) >= '0' && month.charAt(1) <= '2')) {
                    check=true;
                } else {
                    throw new IllegalArgumentException("Month must be between 01 and 12");
                }
            }
        } else{
            throw new IllegalArgumentException("This field should be 2 digits");}
        return check;
    }


    /*setter*/
    public void setCard_no(String person_card) {
        checkCard(person_card);
        this.card_no = person_card;
    }
    public void setCVV(String cvv) {
        checkCVV(cvv);
        this.CVV = cvv;
    }
    public void setMonthAndYear(String month,String year){
        checkMonth(month);
        checkYear(year);
        this.month=month;
        this.year=year;
        boolean b=NotexpireDate(month,year);



    }


    public boolean checkCard(String person_card){
        boolean flag=false;
        if(emptyCard(person_card)){
            throw new IllegalArgumentException("Must provide card_no");

        }
        else if(person_card.length() != 16){
            throw new IllegalArgumentException("Card number length must be 16 numbers:");
        }
        for(int i=0;i<person_card.length();i++){
            if(!(person_card.charAt(i) >='0' && person_card.charAt(i)<='9'))
                throw new IllegalArgumentException("Card number must be digits");
            else
                flag=true;
        }
        return flag;

    }
    /*for the check of CVV*/
    public boolean checkCVV(String cvv){
        boolean flag=false;
        if (emptyCVV(cvv)) {
            throw new IllegalArgumentException("Must provide cvv");
        }
        else if (cvv.length() != 3) {
            throw new IllegalArgumentException("CVV length must be  3 digits:");
        }
        for(int i=0;i<cvv.length();i++) {

            if (!(cvv.charAt(i) >= '0' && cvv.charAt(i) <= '9')) {
                throw new IllegalArgumentException("CVV number must be digits");
            } else {
                flag = true;
            }
        }


           return flag;
    }
    public void setYear(String year) {
        checkYear(year);
        this.year = year;
        if(!monthEmpty(month)){
            NotexpireDate(month,year);
        }
        else{
            setMonth(month);
        }
    }
    public void setMonth(String month) {
        checkMonth(month);
        this.month = month;
        if(!yearEmpty(year)){
            NotexpireDate(month,year);
        }
        else{
            setYear(year);
        }
    }
    /*getter*/
    public String getCard_no() {
        return card_no;
    }
    public String getCVV() {
        return CVV;
    }
    public String getYear() {
        if(year!=null){
            return year;
        }
        else{
            return null;
        }

    }
    public String getMonth() {
        if(month!=null){
            return month;
        }
        else{
            return null;
        }
    }
}
