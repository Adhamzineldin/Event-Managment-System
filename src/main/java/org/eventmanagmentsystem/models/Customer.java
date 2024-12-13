package org.eventmanagmentsystem.models;

public class Customer extends User{
    /* data field */
    private final int NUMBER_OF_EVENTS = 5; 
    private EventBooking[] events;
    int eventSize = 0;
    
    /* constructor */
    public Customer(String userName, String email, String role) {
        super(userName, email, role);
        events = new EventBooking[NUMBER_OF_EVENTS];
    }
    
    /* Book event method */
    public void bookEvent(EventBooking event){
        /* check fill array */
        if (eventSize == events.length){
            /* create new array */
            EventBooking[] temp = new EventBooking[eventSize * 2];
            System.arraycopy(events, 0, temp, 0, eventSize);
            
            /* change address of array */
            events = temp;
        }
        
        /* add event for event array */
        events[eventSize++] = event;
    }
}
