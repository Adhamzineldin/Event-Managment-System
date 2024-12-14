package org.eventmanagmentsystem.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Message {
    private int id;
    private String message;
    private User sender;  // Using User object for sender
    private User receiver; // Using User object for receiver
    private String date;   // Date when the message was sent

    // Constructor to initialize the message object
    public Message(int id, String message, User sender, User receiver, String date) {
        this.id = id;
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
    }

    // Getters and setters for the fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Method to convert message object to a string (for file writing)
    @Override
    public String toString() {
        return id + "," + message + "," + sender.getId() + "," + receiver.getId() + "," + date;
    }

    // Utility method to parse a string from the file and convert it to a Message object
    public static Message parseMessage(String messageData, Map<Integer, User> users) {
        String[] parts = messageData.split(",");
        if (parts.length >= 5) {
            int id = Integer.parseInt(parts[0]);
            String message = parts[1];
            int senderId = Integer.parseInt(parts[2]);
            int receiverId = Integer.parseInt(parts[3]);
            String date = parts[4];

            // Fetch User objects from a map (you need a way to retrieve users by their ID)
            User sender = users.get(senderId);
            User receiver = users.get(receiverId);

            if (sender != null && receiver != null) {
                return new Message(id, message, sender, receiver, date);
            }
        }
        return null;
    }

    // Utility method to get the current date as a String in the desired format (e.g., yyyy-MM-dd HH:mm:ss)
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date()); // Returns current date and time
    }
}
