package org.eventmanagmentsystem.models;

import org.eventmanagmentsystem.services.AdminService;
import org.eventmanagmentsystem.services.EventService;
import org.eventmanagmentsystem.services.SmtpEmailService;
import org.eventmanagmentsystem.services.UserService;

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
    public void bookEvent(Event event) {
        // Add the event through the EventService
        eventService.addEvent(event);
        SmtpEmailService emailService = new SmtpEmailService();
        UserService userService = new UserService();
        ProjectManager projectManager = (ProjectManager) userService.getUserById(event.getManagerId());
        ServiceProvider serviceProvider = (ServiceProvider) userService.getUserById(event.getServiceProviderId());
        emailService.sendEventDetails(event, this.getEmail(), projectManager.getEmail(), serviceProvider.getEmail());

    }

    // Method to cancel an event (update status to "canceled")
    public boolean cancelEvent(int eventId) {
        // Update the event status to "canceled" instead of deleting
        return eventService.updateEventStatus(eventId, "canceled");
    }

    // Method to view all events associated with this customer
    public List<Event> getEvents() {
        return eventService.getEventsByUser(this); // Get events where this customer is associated
    }

    // Method to view the details of a specific event
    public Event getEventDetails(int eventId) {
        return eventService.getEventById(eventId); // Get event by ID
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
        return true; // In a real implementation, this would process payment
    }
}
