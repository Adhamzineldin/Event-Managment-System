package org.eventmanagmentsystem.services;

import org.eventmanagmentsystem.models.*;

import java.io.*;
import java.util.*;

public class UserService {

    private static final String USERS_FILE_PATH = "data/users.txt";

    public User getUserById(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 5) {
                    int userId = Integer.parseInt(userData[0].trim());
                    if (userId == id) {
                        String username = userData[1].trim();
                        String password = userData[2].trim();
                        String email = userData[3].trim();
                        String role = userData[4].trim();
                        return new EmptyUser(userId, username, password, email, role);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getProjectManagerForCustomer(int customerId) {
        return getUserById(1); // Assume project manager ID = 1 for simplicity
    }
}
