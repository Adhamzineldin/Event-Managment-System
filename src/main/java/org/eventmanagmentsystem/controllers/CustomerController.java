package org.eventmanagmentsystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.eventmanagmentsystem.models.User;
import org.eventmanagmentsystem.services.EventService;
import org.eventmanagmentsystem.models.Event; // Assuming you have this import for Event model

import java.awt.event.ActionEvent;
import java.util.List; // Add this import for List

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
    private Button contactProjectManagerButton;
    @FXML
    private Button bookEventButton;
    @FXML
    private Button manageBookingButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Label feedbackMessage;

    private User currentUser;
    private EventService eventService;

    public CustomerController() {
        this.eventService = EventService.getInstance();
    }

    @FXML
    public void initialize() {
        currentUser = getCurrentUser(); // Example method, replace with real logic
        customerName.setText(currentUser.getUserName());
        customerEmail.setText(currentUser.getEmail());
    }

    public void setUser(User user) {
        this.currentUser = user;
        customerName.setText(currentUser.getUserName());
        customerEmail.setText(currentUser.getEmail());
    }

    @FXML
    private void handleBookEvent() {
        String eventDate = eventDateField.getText();
        String eventDuration = eventDurationField.getText();
        String seats = seatsField.getText();
        String message = messageField.getText();

        if (eventDate.isEmpty() || eventDuration.isEmpty() || seats.isEmpty()) {
            feedbackMessage.setText("Please fill all event details!");
            return;
        }

        // Validate numeric inputs
        try {
            Integer.parseInt(eventDuration);
            Integer.parseInt(seats);
        } catch (NumberFormatException e) {
            feedbackMessage.setText("Please enter valid numbers for duration and seats.");
            return;
        }

        // Book event logic
        boolean isBooked = eventService.bookEvent(currentUser, eventDate, eventDuration, seats, message);

        if (isBooked) {
            feedbackMessage.setText("Event successfully booked!");
        } else {
            feedbackMessage.setText("Event booking failed. Try again.");
        }
    }

    @FXML
    private void handleManageBooking() {
        List<Event> customerEvents = eventService.getEventsByUser(currentUser);
        if (customerEvents.isEmpty()) {
            feedbackMessage.setText("You have no events to manage.");
        } else {
            StringBuilder eventsList = new StringBuilder("Your Events:\n");
            for (Event event : customerEvents) {
                eventsList.append("Event ID: ").append(event.getEventId())
                        .append(", Date: ").append(event.getEventDate())
                        .append(", Status: ").append(event.getStatus())
                        .append("\n");
            }
            feedbackMessage.setText(eventsList.toString());
        }
    }

    @FXML
    private void handleContactProjectManager() {
        String message = messageField.getText();
        if (message.isEmpty()) {
            feedbackMessage.setText("Please enter a message.");
            return;
        }
        // Logic to send the message to the Project Manager
        boolean sent = eventService.contactProjectManager(currentUser, message);
        if (sent) {
            feedbackMessage.setText("Message sent to Project Manager.");
        } else {
            feedbackMessage.setText("Failed to send message.");
        }
    }

    @FXML
    private void handleLogout() {
        currentUser = null;
        feedbackMessage.setText("Logged out successfully.");
        // Add logic to navigate to login screen or reset fields if necessary
    }

    private User getCurrentUser() {
        // Simulate fetching the logged-in user
        return new User(1, "John Doe", "password", "john.doe@example.com", "Customer");
    }

    public void handleLogout(ActionEvent event) {
        try {
            // Load the login page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../fxml/LoginPage.fxml"));
            Parent root = loader.load();

            // Get the current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the login scene
            stage.setScene(new Scene(root));
            stage.setTitle("Login Page");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
