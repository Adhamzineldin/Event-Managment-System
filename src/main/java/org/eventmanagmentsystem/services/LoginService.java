package org.eventmanagmentsystem.services;

import javafx.scene.control.Label;
import org.eventmanagmentsystem.factories.UserFactory;
import org.eventmanagmentsystem.models.User;
import org.eventmanagmentsystem.utils.ValidatorUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginService {

    private final String userFilePath = "data/users.txt"; // Path to the text file containing user data

    public User login(String username, String password, Label errorLabel) throws IOException {

        // Authenticate user
        try (BufferedReader br = new BufferedReader(new FileReader(userFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails.length == 5) { // Format: "id,username,password,email,role"
                    int id = Integer.parseInt(userDetails[0].trim());
                    String storedUsername = userDetails[1].trim();
                    String storedPassword = userDetails[2].trim();
                    String email = userDetails[3].trim();
                    String role = userDetails[4].trim();

                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        // Use the factory to create a user without saving the password
                        return UserFactory.createUser(id, storedUsername, email, role);
                    }
                }
            }
        }

        errorLabel.setText("Invalid credentials.");
        return null;
    }
}
