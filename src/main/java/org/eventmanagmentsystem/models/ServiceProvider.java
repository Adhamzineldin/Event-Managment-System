package org.eventmanagmentsystem.models;

import org.eventmanagmentsystem.services.EventService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceProvider extends User {
    private EventService eventService;

    public ServiceProvider(int id, String userName, String password, String email, String role) {
        super(id, userName, password, email, role);
        this.eventService = EventService.getInstance();
    }

    // Method to get all events associated with the service provider
    public List<Event> getEvents() {
        return eventService.getEventsByUser(this); // Get events where this service provider is associated
    }

    // Method to view the details of a specific event
    public Event getEventDetails(int eventId) {
        return eventService.getEventById(eventId); // Get event by ID (returns Event object)
    }

    // Method to view the event history (past events)
    public List<Event> getEventHistory() {
        List<Event> serviceProviderEvents = eventService.getEventsByUser(this);
        List<Event> pastEvents = new ArrayList<>();
        Date currentDate = new Date();

        // Filter past events based on event date and status
        for (Event event : serviceProviderEvents) {
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
