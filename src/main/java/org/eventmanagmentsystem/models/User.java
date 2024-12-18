package org.eventmanagmentsystem.models;

import org.eventmanagmentsystem.services.MessageService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public abstract class User {
    private String userName;
    private String role;
    private String email;
    private String password;
    private int id;
    private ArrayList<Event> events = new ArrayList<>();
    private MessageService messageService;

    public User(int id, String userName, String password, String email, String role) {
        this.userName = userName;
        this.role = role;
        this.email = email;
        this.id = id;
        this.password = password;
        this.messageService = new MessageService();
    }

    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }
    
    

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
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

    // In the User class or its subclasses like Customer, ProjectManager, etc.
    @Override
    public String toString() {
        return getId() + "," + getUserName() + "," + getPassword() + "," + getEmail() + "," + getRole();
    }


     // General method to send a message to any user (Customer or ServiceProvider) object
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
                System.out.println("From: " + message.getSender().getUserName() + " | To: " + message.getReceiver().getUserName() + " | Message: " + message.getMessage() + " | Date: " + message.getDate());
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

}
