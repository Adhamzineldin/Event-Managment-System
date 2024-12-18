package org.eventmanagmentsystem.app;

import org.eventmanagmentsystem.models.*;

import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) {
         ProjectManager projectManager = new ProjectManager(3, "JohnDoe", "password", "asas@gmail.com", "manager");
         Customer customer = new Customer(2, "JaneDoe", "password", "mohalya3@gmail.com", "customer");
         Date now = new Date();
        Event event = new Event( 2, now, 2, 10, 100.0, "pending", 50.0);
        customer.bookEvent(event);
        List<Event> events = customer.getEvents();
        for (Event e : events) {
            System.out.println(e);
        }

    }
}
