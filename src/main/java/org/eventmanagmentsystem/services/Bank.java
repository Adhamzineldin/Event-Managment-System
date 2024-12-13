package org.eventmanagmentsystem.services;
public class Bank {
    private String card_no;
    private String CVV;
    private int year;
    private int month;
    public Bank( String card, String cvv, int year, int month) {
        this.card_no = card;
        if(!checkCard()) {
            throw new IllegalArgumentException("Card number is not valid");
        }
        this.CVV=cvv;
        if(!checkCVV()){
            throw new IllegalArgumentException("Invalid CVV");
        }

            if(month<=12 && month>=1){
                this.month = month;
            }
            else{
                throw new IllegalArgumentException("Month must be between 1 and 12");
            }
            this.year=year;
        }
        public Bank(){

        }
        /*for the check of the card number*/
        public boolean checkCard(){
            if(emptyCard()){
                throw new IllegalArgumentException("Must provide card_no");
            }
            else if(card_no.length() != 16){
                throw new IllegalArgumentException("Card number length must be 16 numbers:");
            }
            else
                return true;
        }
    /*for the check of CVV*/
    public boolean checkCVV(){
        if (emptyCVV()) {
            throw new IllegalArgumentException("Must provide cvv");
        }
        else if (CVV.length() != 3) {
            throw new IllegalArgumentException("CVV length must be  3 digits:");
        }
        else
            return true;
    }


    /*emptyCard is a function
    that tells us that the card_no is null,
    same as it in CVV also
     */
    private boolean emptyCard(){
        return (card_no == null);
    }
    private boolean emptyCVV(){
        return CVV == null;
    }
    /*setter*/
    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }
    public void setCVV(String cvv) {
        this.CVV = cvv;
    }
    public void setYear(int year) {
        this.year=year;
    }
    public void setMonth(int month) {
        this.month=month;
    }
    /*getter*/
    public String getCard_no() {
        return card_no;
    }
    public String getCVV() {
        return CVV;
    }
    public int getYear() {
        return year;
    }
    public int getMonth() {
        return month;
    }
}
