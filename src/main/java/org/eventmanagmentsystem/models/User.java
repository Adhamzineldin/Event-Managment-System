package org.eventmanagmentsystem.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import org.eventmanagmentsystem.services.MessageService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class User {
    private StringProperty userName;
    private StringProperty role;
    private StringProperty email;
    private String password;
    private int id;
    private ArrayList<Event> events = new ArrayList<>(); // List to store user's events
    private MessageService messageService;

    public User(int id, String userName, String password, String email, String role) {
        this.userName = new SimpleStringProperty(userName);
        this.role = new SimpleStringProperty(role);
        this.email = new SimpleStringProperty(email);
        this.id = id;
        this.password = password;
        this.messageService = new MessageService();
    }

    // Getters and Setters
    public String getUserName() {
        return userName.get();
    }

    public String getRole() {
        return role.get();
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email.get();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    // ToString method to represent User data
    @Override
    public String toString() {
        return getId() + "," + getUserName() + "," + getPassword() + "," + getEmail() + "," + getRole();
    }

    // Add event to user's event list
    public void addEvent(Event event) {
        events.add(event);
    }

    // Remove event from user's event list
    public boolean removeEvent(Event event) {
        return events.remove(event);
    }

    // Get list of events associated with the user
    public List<Event> getEvents() {
        return events;
    }

    // General method to send a message to any user (Customer or ServiceProvider)
    public boolean sendMessage(User recipient, String messageContent) {
        // Create a new message with the current date
        Message message = new Message(
                generateUniqueMessageId(),
                messageContent,
                this,           // Sender is now a User object
                recipient,      // Receiver is now a User object
                Message.getCurrentDate() // Use current date for the message
        );

        return messageService.addMessage(message);
    }

    // General method to receive a message from any user (Customer or ServiceProvider) object
    public void receiveMessage(User sender) {
        // Fetch messages from the sender
        for (Message message : messageService.viewMessagesBySender(sender)) {
            if (message.getReceiver().getId() == this.getId()) {
                System.out.println("Message from " + sender.getUserName() + ": " + message.getMessage());
            }
        }
    }

    // Method to get all chat history with a user (Customer or ServiceProvider)
    public void getChatHistoryWithUser(User user) {
        List<Message> allMessages = messageService.getAllMessages();

        // Filter messages where the ProjectManager is either the sender or receiver
        for (Message message : allMessages) {
            if ((message.getSender().getId() == this.getId() && message.getReceiver().getId() == user.getId()) ||
                    (message.getReceiver().getId() == this.getId() && message.getSender().getId() == user.getId())) {
                System.out.println("From: " + message.getSender().getUserName() + " | To: " + message.getReceiver().getUserName() +
                        " | Message: " + message.getMessage() + " | Date: " + message.getDate());
            }
        }
    }

    // Method to generate a unique message ID based on a simple approach
    private int generateUniqueMessageId() {
        String datePart = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int randomPart = new Random().nextInt(100); // 2-digit random number (0-99)

        // Combine the date and random parts, ensuring it's smaller than Integer.MAX_VALUE
        String uniqueIdString = datePart.substring(2) + String.format("%02d", randomPart); // Get the last 6 digits of the date and add the random part
        return Integer.parseInt(uniqueIdString); // Convert to integer
    }

    // Correct implementation for getUsername method
    public String getUsername() {
        return this.userName.get();
    }

    // Observable Properties
    public StringProperty userNameProperty() {
        return userName;
    }

    public StringProperty roleProperty() {
        return role;
    }

    public StringProperty emailProperty() {
        return email;
    }


}
