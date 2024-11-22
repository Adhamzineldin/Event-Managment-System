package org.eventmanagmentsystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.eventmanagmentsystem.models.User;
import org.eventmanagmentsystem.services.LoginService;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label alertLabel;

    public void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        LoginService loginService = new LoginService();
        User user = loginService.login(username, password, alertLabel);

        if (user != null) {  // If login is successful
            String role = user.getRole();
            FXMLLoader loader = new FXMLLoader();

            // Load the appropriate FXML file based on role
            if (role.equals("admin")) {
                loader.setLocation(getClass().getResource("/fxml/AdminPage.fxml"));
            } else if (role.equals("customer")) {
                loader.setLocation(getClass().getResource("/fxml/CustomerPage.fxml"));
            }

            // Load the scene and set it in the stage
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

            // Pass the user data to the next controller
            if (role.equals("admin")) {
                AdminController adminController = loader.getController();
                adminController.setUser(user);
            } else if (role.equals("customer")) {
                CustomerController customerController = loader.getController();
                customerController.setUser(user);
            }
        } else {
            alertLabel.setOpacity(1);
        }
    }
}
