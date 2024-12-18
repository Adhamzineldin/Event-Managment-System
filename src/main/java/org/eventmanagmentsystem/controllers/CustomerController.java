package org.eventmanagmentsystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.eventmanagmentsystem.models.Customer;
import org.eventmanagmentsystem.models.Event;
import org.eventmanagmentsystem.models.User;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomerController {

    @FXML
    private Label customerName;
    @FXML
    private Label customerEmail;
    @FXML
    private TextField eventDateField;
    @FXML
    private TextField eventDurationField;
    @FXML
    private TextField seatsField;
    @FXML
    private TextArea messageField;
    @FXML
    private Label feedbackMessage;

    private Customer customer;

    @FXML
    public void initialize() {
        // Use the customer object passed from the login process
        if (customer != null) {
            customerName.setText(customer.getUserName());
            customerEmail.setText(customer.getEmail());
        }
    }

    @FXML
    private void handleBookEvent() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date eventDate = dateFormat.parse(eventDateField.getText());
            int duration = Integer.parseInt(eventDurationField.getText());
            int seats = Integer.parseInt(seatsField.getText());
            double cost = calculateCost(duration, seats);
            double serviceProviderCost = calculateServiceProviderCost(duration, seats);
            String message = messageField.getText();

            if (duration <= 0 || seats <= 0) {
                showFeedback("Duration and seats must be positive numbers.", "error");
                return;
            }

            // Create an event for the customer
            Event event = new Event(
                    1, // Sample customer ID, you can use customer.getId()
                    eventDate, duration, seats, cost, "Pending", serviceProviderCost
            );

            // Book the event through the customer model
            customer.bookEvent(event);
            clearFields();
            showFeedback("Event booked successfully! Total cost: " + event.calculateTotalCost(), "success");

        } catch (ParseException e) {
            showFeedback("Invalid event date format. Use yyyy-MM-dd.", "error");
        } catch (NumberFormatException e) {
            showFeedback("Duration and seats must be valid numbers.", "error");
        }
    }

    @FXML
    private void handleManageBooking() {
        // You can list the customer's events here or add other functionalities
        showFeedback("Manage Booking functionality coming soon!", "info");
    }

    @FXML
    private void handleLogout() throws IOException {
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

    private void clearFields() {
        eventDateField.clear();
        eventDurationField.clear();
        seatsField.clear();
        messageField.clear();
    }

    private double calculateCost(int duration, int seats) {
        return duration * seats * 50.0; // Sample calculation
    }

    private double calculateServiceProviderCost(int duration, int seats) {
        return duration * seats * 20.0; // Sample calculation
    }

    private void showFeedback(String message, String type) {
        feedbackMessage.setText(message);
        feedbackMessage.getStyleClass().removeAll("success", "error", "info");
        feedbackMessage.getStyleClass().add(type);
    }

    public void setUser(User user) {
        if (user instanceof Customer) {
            this.customer = (Customer) user;
        } else {
            throw new IllegalArgumentException("Invalid user type.");
        }
    }
}
