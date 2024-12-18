package org.eventmanagmentsystem.services;

import org.eventmanagmentsystem.models.Message;
import org.eventmanagmentsystem.models.User;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MessageService {

    private static final String FILE_PATH = "data/messages.txt";

    // Method to add a message
    public boolean addMessage(Message message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(message.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to remove a message by ID
    public boolean removeMessage(int messageId) {
        List<Message> messages = getAllMessages();
        boolean messageRemoved = messages.removeIf(message -> message.getId() == messageId);

        if (messageRemoved) {
            return saveAllMessages(messages);
        }
        return false;
    }

    // Method to get all messages
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            Map<Integer, User> users = getUsersMap(); // Get a map of all users

            while ((line = reader.readLine()) != null) {
                Message message = Message.parseMessage(line, users);
                if (message == null) {
                    System.out.println("Failed to parse message: " + line);
                } else {
                    messages.add(message);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
        return messages;
    }


    // Method to save all messages to the file
    private boolean saveAllMessages(List<Message> messages) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Message message : messages) {
                writer.write(message.toString());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Methods to view messages by sender or receiver
    public List<Message> viewMessagesBySender(User sender) {
        return getAllMessages().stream()
                .filter(message -> message.getSender().equals(sender))
                .collect(Collectors.toList());
    }

    public List<Message> viewMessagesByReceiver(User receiver) {
        return getAllMessages().stream()
                .filter(message -> message.getReceiver().equals(receiver))
                .collect(Collectors.toList());
    }

    // Utility method to get all users (you'll need to implement fetching users, for now, it returns an empty map)
    private Map<Integer, User> getUsersMap() {
        Map<Integer, User> users = new HashMap<>();
        // Example: Populate the map with users from your database or data source
        // users.put(user.getId(), user);
        return users;
    }
}
