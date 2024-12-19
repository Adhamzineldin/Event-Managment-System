package org.eventmanagmentsystem.services;

import org.eventmanagmentsystem.models.*;
import org.eventmanagmentsystem.factories.UserFactory;

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

    // Other members remain unchanged

    private EventService() {
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


    // Load events from the file
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
        if (parts.length >= 10) {
            int eventId = Integer.parseInt(parts[0]);
            int customerId = Integer.parseInt(parts[1]);
            int managerId = Integer.parseInt(parts[2]);
            int serviceProviderId = Integer.parseInt(parts[3]);

            // Use SimpleDateFormat to parse the date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the format if needed
            Date eventDate = null;
            try {
                eventDate = dateFormat.parse(parts[4]); // Parse the date from the string
            } catch (ParseException e) {
                System.out.println("Error parsing date: " + e.getMessage());
            }

            int eventDuration = Integer.parseInt(parts[5]);
            int seats = Integer.parseInt(parts[6]);
            double cost = Double.parseDouble(parts[7]);
            String status = parts[8];
            double serviceProviderCost = Double.parseDouble(parts[9]);

            return new Event(eventId, customerId, managerId, serviceProviderId, eventDate, eventDuration, seats, cost, status, serviceProviderCost);
        }
        return null;
    }


    // Load users from the file using the factory
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

    // Parse a line of text to create a User object using the factory
    private User parseUser(String userData) {
        String[] parts = userData.split(",");
        if (parts.length >= 5) {
            int userId = Integer.parseInt(parts[0]);
            String userName = parts[1];
            String password = parts[2];
            String email = parts[3];
            String role = parts[4];

            // Use the UserFactory to create users based on the role
            return new EmptyUser(userId, userName, password, email, role);
        }
        return null;
    }

    // Save the events back to the file
    private void saveEventsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EVENTS_FILE_PATH))) {
            for (Event event : events) {
                writer.write(event.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving events to file: " + e.getMessage());
        }
    }

    // Add a new event and assign a random manager and service provider
    public boolean addEvent(Event event) {
        // Assign random manager and service provider
        event.setEventId(generateUniqueId());
        assignRandomManagerAndServiceProvider(event);

        if (events.add(event)) {
            saveEventsToFile();
            return true;
        }
        return false;
    }

    private int generateUniqueId() {
        String datePart = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int randomPart = new Random().nextInt(100); // 2-digit random number (0-99)

        // Combine the date and random parts, ensuring it's smaller than Integer.MAX_VALUE
        String uniqueIdString = datePart.substring(2) + String.format("%02d", randomPart); // Get the last 6 digits of the date and add the random part
        return Integer.parseInt(uniqueIdString); // Convert to integer
    }

    // Randomly assign a manager and service provider to the event
    private Event assignRandomManagerAndServiceProvider(Event event) {
        Random random = new Random();
        List<User> managers = new ArrayList<>();
        List<User> serviceProviders = new ArrayList<>();


        // Separate users into managers and service providers
        for (User user : users) {
            if (Objects.equals(user.getRole(), "manager")) {
                managers.add(user);
            } else if (Objects.equals(user.getRole(), "provider")) {
                serviceProviders.add(user);
            }
        }
        System.out.println("Users: " + users.size());
        System.out.println("Managers: " + managers.size());
        System.out.println("Service Providers: " + serviceProviders.size());

        // Randomly assign manager and service provider
        if (!managers.isEmpty() && !serviceProviders.isEmpty()) {
            User randomManager = managers.get(random.nextInt(managers.size()));
            User randomServiceProvider = serviceProviders.get(random.nextInt(serviceProviders.size()));

            // Set the random manager and service provider
            event.setManagerId(randomManager.getId());
            event.setServiceProviderId(randomServiceProvider.getId());
        }
        return event;
    }

    // Update an existing event
    public boolean updateEvent(int eventId, Date eventDate, int eventDuration, int seats, double cost, double serviceProviderCost) {
        for (Event event : events) {
            if (event.getEventId() == eventId) {
                event.updateEventDetails(eventDate, eventDuration, seats, cost, serviceProviderCost);
                saveEventsToFile();
                return true;
            }
        }
        return false;
    }

    // Update the status of an event
    public boolean updateEventStatus(int eventId, String newStatus) {
        for (Event event : events) {
            if (event.getEventId() == eventId) {
                event.updateStatus(newStatus);
                saveEventsToFile();
                return true;
            }
        }
        return false;
    }

    // Get all events
    public List<Event> getAllEvents() {
        return events;
    }

    // Get event by ID
    public Event getEventById(int eventId) {
        for (Event event : events) {
            if (event.getEventId() == eventId) {
                return event;
            }
        }
        return null;
    }

    // Delete event by setting its status to "canceled"
    public boolean cancelEvent(int eventId) {
        for (Event event : events) {
            if (event.getEventId() == eventId) {
                event.updateStatus("canceled");
                saveEventsToFile();
                return true;
            }
        }
        return false;
    }

    public List<Event> getEventsByUser(User user) {
        List<Event> userEvents = new ArrayList<>();

        for (Event event : events) {
            if (event.getCustomerId() == user.getId()) {
                userEvents.add(event); // User is the customer
            } else if (event.getManagerId() == user.getId()) {
                userEvents.add(event); // User is the manager
            } else if (event.getServiceProviderId() == user.getId()) {
                userEvents.add(event); // User is the service provider
            }
        }
        
       

        return userEvents;
    }
}
