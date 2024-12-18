package org.eventmanagmentsystem.models;

import org.eventmanagmentsystem.services.EventService;
import org.eventmanagmentsystem.services.SmtpEmailService;
import org.eventmanagmentsystem.services.UserService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Customer extends User {

    private EventService eventService;

    public Customer(int id, String userName, String password, String email, String role) {
        super(id, userName, password, email, role);
        this.eventService = EventService.getInstance();
    }

    // Method to book an event
    // Inside the Customer class

    public boolean bookEvent(Event event) {
        boolean isEventAdded = eventService.addEvent(event);
        if (isEventAdded) {
            SmtpEmailService emailService = new SmtpEmailService();
            UserService userService = new UserService();
            ProjectManager projectManager = (ProjectManager) userService.getUserById(event.getManagerId());
            ServiceProvider serviceProvider = (ServiceProvider) userService.getUserById(event.getServiceProviderId());

            // Send event details to customer, project manager, and service provider
            emailService.sendEventDetails(event, this.getEmail(), projectManager.getEmail(), serviceProvider.getEmail());

            // Save event details to events.txt
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/events.txt", true))) {
                writer.write(event.toString());
                writer.newLine(); // Ensure each event is on a new line
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }
        return false;
    }


    // Method to cancel an event (update status to "canceled")
    public boolean cancelEvent(int eventId) {
        // Update the event status to "canceled"
        return eventService.updateEventStatus(eventId, "canceled");
    }

    // Method to view all events associated with this customer
    public List<Event> getEvents() {
        return eventService.getEventsByUser(this); // Get events where this customer is associated
    }

    // Method to view the details of a specific event
    public Event getEventDetails(int eventId) {
        return eventService.getEventById(eventId); // Get event by ID and return the Event object
    }

    // Method to view the event history (past events)
    public List<Event> getEventHistory() {
        List<Event> customerEvents = eventService.getEventsByUser(this);
        List<Event> pastEvents = new ArrayList<>();
        Date currentDate = new Date();

        // Filter past events based on event date and status
        for (Event event : customerEvents) {
            if (event.getEventDate().before(currentDate) && event.getStatus().equalsIgnoreCase("completed")) {
                pastEvents.add(event);
            }
        }

        return pastEvents;
    }

    // Method to update event details
    public boolean updateEvent(int eventId, Date eventDate, int eventDuration, int seats, double cost, double serviceProviderCost) {
        return eventService.updateEvent(eventId, eventDate, eventDuration, seats, cost, serviceProviderCost);
    }

    // Placeholder method to simulate payment for the event
    public boolean payEvent(int eventId) {
        // Simulate payment processing, in reality, this would integrate with a payment gateway
        return true;
    }
}
