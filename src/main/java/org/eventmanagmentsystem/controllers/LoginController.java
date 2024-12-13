package org.eventmanagmentsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.eventmanagmentsystem.models.User;
import org.eventmanagmentsystem.services.GUIService;
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


           String fxmlPath = "/fxml/" + role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase() + "Page.fxml";
           loader.setLocation(getClass().getResource(fxmlPath));


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

    public void signUp(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/RegisterPage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) usernameField.getScene().getWindow();
        double previousWidth = stage.getWidth();
        double previousHeight = stage.getHeight();
        stage.setScene(scene);
        stage.setWidth(previousWidth);
        stage.setHeight(previousHeight);
        stage.show();



    }
}
