package org.eventmanagmentsystem.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.eventmanagmentsystem.models.Customer;
import org.eventmanagmentsystem.models.Event;
import org.eventmanagmentsystem.models.Message;
import org.eventmanagmentsystem.models.User;
import org.eventmanagmentsystem.services.UserService;

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
    @FXML private TextField updateManagerIdField;   // New Manager ID field
    @FXML private TextField updateManagerNameField; // New Manager Name field

    // Chat Section Fields
    @FXML private TextArea chatMessages;
    @FXML private TextField chatInput;

    @FXML private TableColumn<Event, String> eventIdColumn;
    @FXML private TableColumn<Event, Date> eventDateColumn;
    @FXML private TableColumn<Event, Integer> eventDurationColumn;
    @FXML private TableColumn<Event, Integer> eventSeatsColumn;
    @FXML private TableColumn<Event, Integer> managerIdColumn;    // New Manager ID column
    @FXML private TableColumn<Event, String> managerNameColumn;  // New Manager Name column

    private Customer customer;
    UserService userService = new UserService();

    @FXML
    public void initialize() {
        // Customize initialization if needed
        showEventBooking();

        // Set up the table columns with manager information
        eventIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEventId())));
        eventDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEventDate()));
        eventDurationColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEventDuration()));
        eventSeatsColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSeats()));
        managerIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(String.valueOf(cellData.getValue().getManagerId())));
        managerNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(userService.getUserById(cellData.getValue().getManagerId()).getUserName()));

        // Add a listener for selecting a row and filling the fields
        eventsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateUpdateFields(newValue);
            }
        });
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

            Event event = new Event(customer.getId(), eventDate, duration, seats, calculateCost(duration, seats), "Pending", calculateServiceProviderCost(duration, seats));
            customer.bookEvent(event);
            clearBookingFields();
            showFeedback(feedbackMessage, "Event booked successfully!", "success");

        } catch (ParseException | NumberFormatException e) {
            showFeedback(feedbackMessage, "Invalid input. Ensure correct formats.", "error");
        }
    }

    @FXML
    private void handleViewAndUpdateEvents() {
        List<Event> events = this.customer.getEvents();
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
        List<Event> events = this.customer.getEvents();
        int managerId = events.get(events.size() - 1).getManagerId();
        User manager = userService.getUserById(managerId);
        String message = chatInput.getText();
       
         customer.sendMessage(manager, message);
         chatInput.clear();
         receiveChatMessage();
    }
    
    @FXML
    private void receiveChatMessage() {
        
        List<Event> events = this.customer.getEvents();
        int managerId = events.get(events.size() - 1).getManagerId();
        User manager = userService.getUserById(managerId);
        
        List<Message> messages = customer.getChatHistoryWithUser(manager);
        for (Message msg : messages) {
            System.out.println(msg.getSender().getUserName() + ": " + msg.getMessage());
            chatMessages.appendText(msg.getSender().getUserName() + ": " + msg.getMessage() + "\n");
        }
    }
    
    

    @FXML
    private void showEventBooking() {
       
        switchSection(eventBookingSection);
    }

    @FXML
    private void showViewUpdateEvents() {
        handleViewAndUpdateEvents();
        switchSection(viewUpdateEventsSection);
    }

    @FXML
    private void showChatSection() {
        switchSection(chatSection);
        receiveChatMessage();
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
        updateEventIdField.clear();
        updateManagerIdField.clear();
        updateManagerNameField.clear();
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

    // Populates update fields when an event is clicked
    private void populateUpdateFields(Event event) {
        if (event != null) {
            updateEventIdField.setText(String.valueOf(event.getEventId()));
            updateEventDateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(event.getEventDate()));
            updateEventDurationField.setText(String.valueOf(event.getEventDuration()));
            updateSeatsField.setText(String.valueOf(event.getSeats()));
            updateManagerIdField.setText(String.valueOf(event.getManagerId()));
            updateManagerNameField.setText(userService.getUserById(event.getManagerId()).getUserName());
        }
    }

}
