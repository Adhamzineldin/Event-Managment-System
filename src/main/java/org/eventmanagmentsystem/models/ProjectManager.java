package org.eventmanagmentsystem.models;

import org.eventmanagmentsystem.services.EventService;
import org.eventmanagmentsystem.services.MessageService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ProjectManager extends User {

    private EventService eventService;

    public ProjectManager(int id, String userName, String password, String email, String role) {
        super(id, userName, password, email, role);
        this.eventService = EventService.getInstance(); // Instantiate the message service
    }

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


}
