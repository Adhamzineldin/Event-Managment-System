package org.eventmanagmentsystem.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.eventmanagmentsystem.factories.UserFactory;
import org.eventmanagmentsystem.models.*;
import org.eventmanagmentsystem.services.UserService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class ServiceProviderController {


    @FXML private VBox viewUpdateEventsSection;
    @FXML private VBox chatSection;


    @FXML private Label feedbackMessage;

    // View and Update Events Section Fields
    @FXML private TableView<Event> eventsTable;
    @FXML private TextField updateEventDateField;
    @FXML private TextField updateEventDurationField;
    @FXML private TextField updateSeatsField;
    @FXML private TextField updateEventIdField;
    @FXML private TextField updateServiceProviderCost;
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
    @FXML private TableColumn<Event, Double> serviceProviderCost;

    private ServiceProvider serviceProvider;
    UserService userService = new UserService();

    @FXML
    public void initialize() {
        // Customize initialization if needed
       
//        showChatSection();

        // Set up the table columns with manager information
        eventIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEventId())));
        eventDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEventDate()));
        eventDurationColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEventDuration()));
        eventSeatsColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSeats()));
        managerIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(String.valueOf(cellData.getValue().getManagerId())));
        managerNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(userService.getUserById(cellData.getValue().getManagerId()).getUserName()));
        serviceProviderCost.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getServiceProviderCost()));

        // Add a listener for selecting a row and filling the fields
        eventsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateUpdateFields(newValue);
            }
        });
    }


    @FXML
    private void handleViewAndUpdateEvents() {
        List<Event> events = this.serviceProvider.getEvents();
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
            Double serviceProviderCost = Double.parseDouble(updateServiceProviderCost.getText());

            boolean status = serviceProvider.updateEvent(eventId, eventDate, duration, seats, calculateCost(duration, seats), serviceProviderCost);
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
        List<Event> events = this.serviceProvider.getEvents();
        if (events.isEmpty()) {
            return;
        }
        int managerId = events.get(events.size() - 1).getManagerId();
        User manager = userService.getUserById(managerId);
        String message = chatInput.getText();

        serviceProvider.sendMessage(manager, message);
        chatInput.clear();
        receiveChatMessage();
    }

    @FXML
    private void receiveChatMessage() {
        List<Event> events = this.serviceProvider.getEvents();
        if (events.isEmpty()) {
            return;
        }
        int managerId = events.get(events.size() - 1).getManagerId();
        User manager = userService.getUserById(managerId);

        List<Message> messages = serviceProvider.getChatHistoryWithUser(manager);
        chatMessages.clear();
        for (Message msg : messages) {
            chatMessages.appendText(msg.getSender().getUserName() + ": " + msg.getMessage() + " " + msg.getDate() + "\n");
        }
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
       
        viewUpdateEventsSection.setVisible(section == viewUpdateEventsSection);
        chatSection.setVisible(section == chatSection);
        
        viewUpdateEventsSection.setManaged(section == viewUpdateEventsSection);
        chatSection.setManaged(section == chatSection);
    }


    private void clearUpdateFields() {
        updateEventDateField.clear();
        updateEventDurationField.clear();
        updateSeatsField.clear();
        updateEventIdField.clear();
        handleViewAndUpdateEvents();
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
        
        if (user instanceof ServiceProvider) {
            this.serviceProvider = (ServiceProvider) user;
        } else {
            throw new IllegalArgumentException("Invalid user type.");
        }
        if (serviceProvider != null) {
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
            updateServiceProviderCost.setText(String.valueOf(event.getServiceProviderCost()));

        }
    }


    public void signOut(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/LoginPage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) updateSeatsField.getScene().getWindow();
        double previousWidth = stage.getWidth();
        double previousHeight = stage.getHeight();
        stage.setScene(scene);
        stage.setWidth(previousWidth);
        stage.setHeight(previousHeight);
        stage.show();
    }

   
}
