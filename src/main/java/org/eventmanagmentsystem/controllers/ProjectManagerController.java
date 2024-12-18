package org.eventmanagmentsystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.eventmanagmentsystem.models.Event;
import org.eventmanagmentsystem.models.ProjectManager;
import org.eventmanagmentsystem.models.User;
import org.eventmanagmentsystem.services.UserService;

import java.util.List;

public class ProjectManagerController {

    @FXML
    private TableView<Event> requestTable;
    @FXML
    private TableColumn<Event, Integer> requestIdColumn;
    @FXML
    private TableColumn<Event, String> customerNameColumn;
    @FXML
    private TableColumn<Event, String> eventDetailsColumn;
    @FXML
    private TextField messageField;

    private ProjectManager projectManager;  // Reference to the ProjectManager object

    @FXML
    public void initialize() {
        setupTableColumns();
        loadEventRequests();
    }

    private void setupTableColumns() {
        requestIdColumn.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        customerNameColumn.setCellValueFactory(param -> {
            Event event = param.getValue();
            UserService userService = new UserService();
            User customer = userService.getUserById(event.getCustomerId());
            String name = customer.getUserName();
            return new javafx.beans.property.SimpleStringProperty(name); 
        });
        eventDetailsColumn.setCellValueFactory(param -> {
            Event event = param.getValue();
            String details = "Date: " + event.getEventDate() + " | Seats: " + event.getSeats();
            return new Text(details).textProperty();
        });
    }

    private void loadEventRequests() {
        if (projectManager != null) {
            List<Event> events = projectManager.getEvents();
            requestTable.getItems().setAll(events);
        }
    }

    @FXML
    private void handleForwardToServiceProvider() {
        Event selectedEvent = requestTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            selectedEvent.updateStatus("Forwarded");
            showAlert("Success", "Event forwarded to service provider.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "No event selected.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleViewCustomer() {
        Event selectedEvent = requestTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            String customerInfo = "Customer ID: " + selectedEvent.getCustomerId() + "\n" +
                    "Event Date: " + selectedEvent.getEventDate() + "\n" +
                    "Seats: " + selectedEvent.getSeats();
            showAlert("Customer Details", customerInfo, Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "No event selected.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleGenerateBill() {
        Event selectedEvent = requestTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            int totalCost = selectedEvent.calculateTotalCost();
            showAlert("Bill Generated", "Total Cost: $" + totalCost, Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "No event selected.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleSendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            Event selectedEvent = requestTable.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                // Send message from the ProjectManager to the Customer or ServiceProvider
                UserService userService = new UserService();
                User customer = userService.getUserById(selectedEvent.getCustomerId());  // Assuming Event has a getCustomer() method
                boolean sent = projectManager.sendMessage(customer, message);
                if (sent) {
                    showAlert("Message Sent", "Your message has been sent successfully.", Alert.AlertType.INFORMATION);
                    messageField.clear();
                } else {
                    showAlert("Error", "Failed to send the message.", Alert.AlertType.ERROR);
                }
            }
        } else {
            showAlert("Error", "Message field cannot be empty.", Alert.AlertType.ERROR);
        }
    }

    public void setUser(User projectManager) {
        this.projectManager = (ProjectManager) projectManager;
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
