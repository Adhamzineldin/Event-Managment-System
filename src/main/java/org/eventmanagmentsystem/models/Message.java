package org.eventmanagmentsystem.models;

import org.eventmanagmentsystem.services.UserService;

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
    public static Message parseMessage(String line, Map<Integer, User> users) {
        try {
            String[] parts = line.split(",", -1); // Split by commas, -1 keeps trailing empty elements

            if (parts.length != 5) {
                System.out.println("Invalid message format: " + line);
                return null; // Invalid message format, return null
            }

            // Parse the components
            int messageId = Integer.parseInt(parts[0].trim());
            String messageContent = parts[1].trim();
            int senderId = Integer.parseInt(parts[2].trim());
            int receiverId = Integer.parseInt(parts[3].trim());
            String timestamp = parts[4].trim();

            // Find the sender and receiver from the users map
            UserService userService = new UserService();
            
            User sender = userService.getUserById(senderId);
            User receiver = userService.getUserById(receiverId);

            // If sender or receiver not found, return null
            if (sender == null || receiver == null) {
                System.out.println("User not found for senderId: " + senderId + " or receiverId: " + receiverId);
                return null;
            }

            // Create a Message object with the parsed data
            return new Message(messageId, messageContent, sender, receiver, timestamp);
        } catch (Exception e) {
            System.out.println("Error parsing message: " + line);
            e.printStackTrace();
            return null; // In case of any error, return null
        }
    }


    // Utility method to get the current date as a String in the desired format (e.g., yyyy-MM-dd HH:mm:ss)
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date()); // Returns current date and time
    }
}
