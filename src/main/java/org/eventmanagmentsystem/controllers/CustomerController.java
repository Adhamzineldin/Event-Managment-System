package org.eventmanagmentsystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.eventmanagmentsystem.models.Customer;
import org.eventmanagmentsystem.models.Event;
import org.eventmanagmentsystem.models.User;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomerController {

    // Section Containers
    @FXML private VBox eventBookingSection;
    @FXML private VBox viewUpdateEventsSection;
    @FXML private VBox chatSection;

    // Booking Section Fields
    @FXML private TextField eventDateField;
    @FXML private TextField eventDurationField;
    @FXML private TextField seatsField;
    @FXML private TextArea messageField;
    @FXML private Label feedbackMessage;

    // View and Update Events Section Fields
    @FXML private TableView<Event> eventsTable;
    @FXML private TextField updateEventDateField;
    @FXML private TextField updateEventDurationField;
    @FXML private TextField updateSeatsField;
    @FXML private TextField updateEventIdField;

    // Chat Section Fields
    @FXML private TextArea chatMessages;
    @FXML private TextField chatInput;

    private Customer customer;

    @FXML
    public void initialize() {
        // This can be customized further if needed
    }

    @FXML
    private void handleBookEvent() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date eventDate = dateFormat.parse(eventDateField.getText());
            int duration = Integer.parseInt(eventDurationField.getText());
            int seats = Integer.parseInt(seatsField.getText());

            if (duration <= 0 || seats <= 0) {
                showFeedback(feedbackMessage, "Duration and seats must be positive numbers.", "error");
                return;
            }

            Event event = new Event(1, eventDate, duration, seats, calculateCost(duration, seats), "Pending", calculateServiceProviderCost(duration, seats));
            customer.bookEvent(event);
            clearBookingFields();
            showFeedback(feedbackMessage, "Event booked successfully!", "success");

        } catch (ParseException | NumberFormatException e) {
            showFeedback(feedbackMessage, "Invalid input. Ensure correct formats.", "error");
        }
    }

    @FXML
    private void handleViewAndUpdateEvents() {
        List<Event> events = customer.getEvents();
        eventsTable.getItems().setAll(events);
    }

    @FXML
    private void handleUpdateEvent() {
        try {
            
            int eventId = Integer.parseInt(updateEventIdField.getText());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date eventDate = dateFormat.parse(updateEventDateField.getText());
            int duration = Integer.parseInt(updateEventDurationField.getText());
            int seats = Integer.parseInt(updateSeatsField.getText());

            boolean status = customer.updateEvent(eventId, eventDate, duration, seats, calculateCost(duration, seats), calculateServiceProviderCost(duration, seats));
            if (status) {
                showFeedback(feedbackMessage, "Event updated successfully!", "success");
                clearUpdateFields();
            } else {
                showFeedback(feedbackMessage, "Event not found.", "error");
            }

        } catch (ParseException | NumberFormatException e) {
            showFeedback(feedbackMessage, "Invalid input. Ensure correct formats.", "error");
        }
    }

    @FXML
    private void sendChatMessage() {
        String message = chatInput.getText();
        if (!message.isEmpty()) {
            chatMessages.appendText("You: " + message + "\n");
            chatInput.clear();
        }
    }

    @FXML
    private void showEventBooking() {
        switchSection(eventBookingSection);
    }

    @FXML
    private void showViewUpdateEvents() {
        switchSection(viewUpdateEventsSection);
    }

    @FXML
    private void showChatSection() {
        switchSection(chatSection);
    }

    private void switchSection(VBox section) {
        eventBookingSection.setVisible(section == eventBookingSection);
        viewUpdateEventsSection.setVisible(section == viewUpdateEventsSection);
        chatSection.setVisible(section == chatSection);

        eventBookingSection.setManaged(section == eventBookingSection);
        viewUpdateEventsSection.setManaged(section == viewUpdateEventsSection);
        chatSection.setManaged(section == chatSection);
    }

    private void clearBookingFields() {
        eventDateField.clear();
        eventDurationField.clear();
        seatsField.clear();
        messageField.clear();
    }

    private void clearUpdateFields() {
        updateEventDateField.clear();
        updateEventDurationField.clear();
        updateSeatsField.clear();
    }

    private double calculateCost(int duration, int seats) {
        return duration * seats * 50.0;
    }

    private double calculateServiceProviderCost(int duration, int seats) {
        return duration * seats * 20.0;
    }

    private void showFeedback(Label label, String message, String type) {
        label.setText(message);
        label.getStyleClass().removeAll("success", "error", "info");
        label.getStyleClass().add(type);
    }

    public void setUser(User user) {
        if (user instanceof Customer) {
            this.customer = (Customer) user;
        } else {
            throw new IllegalArgumentException("Invalid user type.");
        }
        if (customer != null) {
            handleViewAndUpdateEvents();
        }
    }
}
