package org.eventmanagmentsystem.services;

import org.eventmanagmentsystem.models.Event;
import org.eventmanagmentsystem.models.User;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EventService {

    private static final String EVENTS_FILE_PATH = "data/events.txt";
    private static final String USERS_FILE_PATH = "data/users.txt";
    private List<Event> events;
    private List<User> users;

    private static EventService instance;

    public EventService() {
        this.events = new ArrayList<>();
        this.users = new ArrayList<>();
        loadEventsFromFile();
        loadUsersFromFile();
    }

    public static EventService getInstance() {
        if (instance == null) {
            synchronized (EventService.class) {
                if (instance == null) {
                    instance = new EventService();
                }
            }
        }
        return instance;
    }

    private void loadEventsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(EVENTS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Event event = parseEvent(line);
                if (event != null) {
                    events.add(event);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading events file: " + e.getMessage());
        }
    }

    private Event parseEvent(String eventData) {
        String[] parts = eventData.split(",");
        if (parts.length >= 11) {
            int eventId = Integer.parseInt(parts[0]);
            int customerId = Integer.parseInt(parts[1]);
            int managerId = Integer.parseInt(parts[2]);
            int serviceProviderId = Integer.parseInt(parts[3]);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date eventDate = null;
            try {
                eventDate = dateFormat.parse(parts[4]);
            } catch (ParseException e) {
                System.out.println("Error parsing date: " + e.getMessage());
            }

            int eventDuration = Integer.parseInt(parts[5]);
            int seats = Integer.parseInt(parts[6]);
            double cost = Double.parseDouble(parts[7]);
            String status = parts[8];
            double serviceProviderCost = Double.parseDouble(parts[9]);
            double tax = Double.parseDouble(parts[10]);

            return new Event(eventId, customerId, managerId, serviceProviderId, eventDate, eventDuration, seats, cost, status, serviceProviderCost, tax);
        }
        return null;
    }

    private void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = parseUser(line);
                if (user != null) {
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users file: " + e.getMessage());
        }
    }

    private User parseUser(String userData) {
        String[] parts = userData.split(",");
        if (parts.length >= 5) {
            int userId = Integer.parseInt(parts[0]);
            String userName = parts[1];
            String password = parts[2];
            String email = parts[3];
            String role = parts[4];

            return new User(userId, userName, password, email, role);
        }
        return null;
    }

    public boolean addEvent(Event event) {
        if (event == null) {
            return false; // Invalid event
        }
        event.setEventId(generateUniqueId());
        events.add(event);
        saveEventsToFile();
        return true;
    }

    private int generateUniqueId() {
        return new Random().nextInt(100000); // Simple random ID generator
    }

    private void saveEventsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EVENTS_FILE_PATH))) {
            for (Event event : events) {
                writer.write(event.toString()); // Ensure Event's toString() method outputs correct format
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving events to file: " + e.getMessage());
        }
    }

    public List<Event> getEventsByUser(User user) {
        List<Event> userEvents = new ArrayList<>();
        for (Event event : events) {
            if (event.getCustomerId() == user.getId() || event.getManagerId() == user.getId() || event.getServiceProviderId() == user.getId()) {
                userEvents.add(event);
            }
        }
        return userEvents;
    }

    public Event getEventById(int eventId) {
        for (Event event : events) {
            if (event.getEventId() == eventId) {
                return event;
            }
        }
        return null; // Event not found
    }

    public boolean updateEvent(int eventId, Date eventDate, int eventDuration, int seats, double cost, double serviceProviderCost) {
        Event eventToUpdate = getEventById(eventId);
        if (eventToUpdate != null) {
            eventToUpdate.setEventDate(eventDate);
            eventToUpdate.setEventDuration(eventDuration);
            eventToUpdate.setSeats(seats);
            eventToUpdate.setCost(cost);
            eventToUpdate.setServiceProviderCost(serviceProviderCost);
            saveEventsToFile();
            return true;
        }
        return false; // Event not found
    }

    public boolean updateEventStatus(int eventId, String status) {
        Event eventToUpdate = getEventById(eventId);
        if (eventToUpdate != null) {
            eventToUpdate.setStatus(status);
            saveEventsToFile();
            return true;
        }
        return false; // Event not found
    }

    public boolean removeEvent(int eventId) {
        Event eventToRemove = getEventById(eventId);
        if (eventToRemove != null) {
            events.remove(eventToRemove);
            saveEventsToFile();
            return true;
        }
        return false; // Event not found
    }

    // Method to retrieve all events for a specific customer
    public Event[] getEventsForCustomer(int customerId) {
        List<Event> customerEvents = new ArrayList<>();
        for (Event event : events) {
            if (event.getCustomerId() == customerId) {
                customerEvents.add(event);
            }
        }
        return customerEvents.toArray(new Event[0]);
    }

    public boolean bookEvent(User currentUser, String eventDate, String eventDuration, String seats, String message) {
        // Implemented in the previous code snippet
        return true;
    }

    public boolean contactProjectManager(User currentUser, String message) {
        // Implemented in the previous code snippet
        return true;
    }
}
