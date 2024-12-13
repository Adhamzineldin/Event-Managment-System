package org.eventmanagmentsystem.models;

public class ServiceProvider extends User{
    
    /* constructor */
    public ServiceProvider(String userName, String email, String role) {
        super(userName, email, role);
    }
    
    /* method for set Price */
    public void setPrice(EventBooking event,double price){
        /* assign price to event object */
        /* rewrite event to file and update it for all stackholders */
    }
    
    /* method for determine ready data for event */
    public void determineEventDate(EventBooking event){
        /* create object data and assign it for event object */
        /* rewrite event to file and update it for all stackholders */
    }
}
