package org.eventmanagmentsystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label alertLabel;

    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password.");
        } else if (authenticate(username, password)) {
            showAlert("Success", "Login successful!");
        } else {
            showAlert("Error", "Invalid username or password.");
        }
    }

    private boolean authenticate(String username, String password) {
        // Replace this logic with actual authentication (e.g., database or API call)
        return "user".equals(username) && "pass".equals(password);
    }

    private void showAlert(String title, String message) {
        alertLabel.setText(message);
    }
}
