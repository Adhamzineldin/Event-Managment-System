package org.eventmanagmentsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.eventmanagmentsystem.models.User;

import java.io.IOException;

public class CustomerController {

    @FXML
    private Label customerName;

    @FXML
    private Label customerEmail;

    @FXML
    private Label customerRole;

    private User user;  // Store the user object

    // Method to set the user
    public void setUser(User user) {
        this.user = user;
        customerName.setText(user.getUserName());
        customerEmail.setText(user.getEmail());
        customerRole.setText(user.getRole());
    }

    public void handleLogout(ActionEvent actionEvent) throws IOException {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/LoginPage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) customerName.getScene().getWindow();
        double previousWidth = stage.getWidth();
        double previousHeight = stage.getHeight();
        stage.setScene(scene);
        stage.setWidth(previousWidth);
        stage.setHeight(previousHeight);
        stage.show();
    }
}
