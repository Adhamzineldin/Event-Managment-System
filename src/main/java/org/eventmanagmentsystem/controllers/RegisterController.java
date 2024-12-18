package org.eventmanagmentsystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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

    public void onRegister() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        ValidatorUtil validatorUtil = new ValidatorUtil();
        String validationResult = validatorUtil.registrationValidator(username, email, password, confirmPassword);

        if (validationResult.equals("Valid")) {
            RegisterService registerService = new RegisterService();
            boolean isRegistered = registerService.saveUser(username, email, password);
            if (isRegistered) {
                showAlert("Registration successful", "success");
            } else {
                showAlert("Registration failed", "error");
            }

        } else {
            showAlert(validationResult, "error");
        }



    }

    public void onLogin() throws IOException {
        GUIService guiService = new GUIService();
        guiService.changeScene("/fxml/LoginPage.fxml", usernameField);
    }

    private void showAlert(String message, String type) {
        alertLabel.setText(message);
        alertLabel.getStyleClass().removeAll("error", "success");
        alertLabel.getStyleClass().add(type);
    }
}