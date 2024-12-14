package org.eventmanagmentsystem.services;

import org.eventmanagmentsystem.factories.UserFactory;
import org.eventmanagmentsystem.models.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class AdminService {

    private static final String FILE_PATH = "data/users.txt";

    // Method to add a user
    public boolean addUser(User user) {
        user.setId(generateUniqueId());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Method to generate a unique ID using the current date and random number (kept small)
    private int generateUniqueId() {
        String datePart = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int randomPart = new Random().nextInt(100); // 2-digit random number (0-99)

        // Combine the date and random parts, ensuring it's smaller than Integer.MAX_VALUE
        String uniqueIdString = datePart.substring(2) + String.format("%02d", randomPart); // Get the last 6 digits of the date and add the random part
        return Integer.parseInt(uniqueIdString); // Convert to integer
    }


    // Method to remove a user by ID and role
    public boolean removeUser(int userId, String role) {
        List<User> users = getAllUsers();
        boolean userRemoved = users.removeIf(user -> user.getId() == userId && user.getRole().equals(role));

        if (userRemoved) {
            return saveAllUsers(users);
        }
        return false;
    }

    // Method to get all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = parseUser(line);
                if (user != null) {
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Method to save all users to the file
    private boolean saveAllUsers(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                writer.write(user.toString());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Methods to view users based on role
    public <T extends User> List<T> viewUsersByRole(Class<T> roleClass) {
        return getAllUsers().stream()
                .filter(user -> roleClass.isInstance(user))
                .map(roleClass::cast)
                .collect(Collectors.toList());
    }

    // Utility method to parse a user from a string
    private User parseUser(String userData) {
        String[] parts = userData.split(",");
        if (parts.length >= 5) {
            int id = Integer.parseInt(parts[0]);
            String userName = parts[1];
            String password = parts[2];
            String email = parts[3];
            String role = parts[4];

            return UserFactory.createUser(id, userName, password, email, role.toLowerCase());
        }
        return null;
    }
}
