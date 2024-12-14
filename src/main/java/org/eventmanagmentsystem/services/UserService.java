package org.eventmanagmentsystem.services;

import org.eventmanagmentsystem.factories.UserFactory;
import org.eventmanagmentsystem.models.User;

import java.io.*;
import java.util.*;

public class UserService {

    // Method to get a User by ID from the user.txt file
    public User getUserById(int id) {
        String fileName = "data/users.txt"; // Path to the user data file
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(","); // Split the line by commas

                if (userData.length == 5) { // Ensure the data is complete
                    int userId = Integer.parseInt(userData[0].trim());
                    if (userId == id) {
                         String username = userData[1].trim();
                        String password = userData[2].trim();
                        String email = userData[3].trim();
                        String role = userData[4].trim();


                        return UserFactory.createUser(userId, username, password, email, role);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Return null if no user with the given ID is found
    }

    // For testing purposes, you can add a main method
    public static void main(String[] args) {
        UserService userService = new UserService();
        User user = userService.getUserById(2); // Example: Get the user with ID 2

        if (user != null) {
            System.out.println("User found: " + user);
        } else {
            System.out.println("User not found.");
        }
    }
}
