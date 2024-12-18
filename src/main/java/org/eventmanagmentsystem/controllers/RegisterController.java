package org.eventmanagmentsystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.eventmanagmentsystem.services.GUIService;
import org.eventmanagmentsystem.services.RegisterService;
import org.eventmanagmentsystem.utils.ValidatorUtil;

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label alertLabel;
    @FXML
    private Label usernameError;
    @FXML
    private Label emailError;
    @FXML
    private Label passwordError;
    @FXML
    private Label confirmPasswordError;

    public void Register() throws IOException {
        // Clear previous error messages
        clearErrorMessages();

        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        ValidatorUtil validatorUtil = new ValidatorUtil();
        String validationResult = validatorUtil.registrationValidator(username, email, password, confirmPassword);

        if (validationResult.equals("Valid")) {
            // Proceed with registration
            RegisterService registerService = new RegisterService();
            boolean isRegistered = registerService.saveUser(username, email, password);
            if (isRegistered) {
                showAlert("Registration successful", "success");
            } else {
                showAlert("Registration failed", "error");
            }
        } else {
            // Display validation error messages
            displayValidationErrors(validationResult);
            showAlert(validationResult, "error");
        }
    }

    private void clearErrorMessages() {
        usernameError.setOpacity(0);
        emailError.setOpacity(0);
        passwordError.setOpacity(0);
        confirmPasswordError.setOpacity(0);
        alertLabel.setOpacity(0);
    }

    private void displayValidationErrors(String validationResult) {
        if (validationResult.contains("Username")) {
            usernameError.setOpacity(1); // Show username error
        }
        if (validationResult.contains("Email")) {
            emailError.setOpacity(1); // Show email error
        }
        if (validationResult.contains("Password")) {
            passwordError.setOpacity(1); // Show password error
        }
        if (validationResult.contains("Confirm Password")) {
            confirmPasswordError.setOpacity(1); // Show confirm password error
        }
    }

    public void goToLogin() throws IOException {
        GUIService guiService = new GUIService();
        guiService.changeScene("/fxml/LoginPage.fxml", usernameField);
    }

    private void showAlert(String message, String type) {
        alertLabel.setText(message);
        alertLabel.getStyleClass().removeAll("error", "success");
        alertLabel.getStyleClass().add(type);
        alertLabel.setOpacity(1); // Show alert label
    }
}
