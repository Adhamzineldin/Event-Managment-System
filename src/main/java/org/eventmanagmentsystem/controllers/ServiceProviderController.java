package org.eventmanagmentsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.eventmanagmentsystem.models.Event;
import org.eventmanagmentsystem.models.ServiceProvider;
import org.eventmanagmentsystem.models.User;
import org.eventmanagmentsystem.services.EventService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ServiceProviderController {

    @FXML
    private TableView<Event> requestTable;

    @FXML
    private TextField setPriceField;

    @FXML
    private TextField readyDateField;

    @FXML
    private Button submitToPMButton;

    @FXML
    private Label feedbackMessage;

    private User user;  // User reference

    @FXML
    public void initialize() {
        feedbackMessage.setText("");
        // You can populate the TableView with the user's events here if needed
    }

    @FXML
    private void handleSubmitToPM() {
        try {
            String priceInput = setPriceField.getText();
            String readyDateInput = readyDateField.getText();

            if (priceInput.isEmpty() || readyDateInput.isEmpty()) {
                showFeedback("Both fields must be filled.", "error");
                return;
            }

            double price = Double.parseDouble(priceInput);
            LocalDate readyDate = LocalDate.parse(readyDateInput);

            if (price <= 0) {
                showFeedback("Price must be positive.", "error");
                return;
            }

            // Submit request with the user context
            submitRequestToPM(price, readyDate);
            clearFields();
            showFeedback("Request submitted successfully!", "success");
        } catch (NumberFormatException e) {
            showFeedback("Price must be a valid number.", "error");
        } catch (DateTimeParseException e) {
            showFeedback("Invalid date format. Use yyyy-MM-dd.", "error");
        }
    }

    private void submitRequestToPM(double price, LocalDate readyDate) {
        if (user instanceof ServiceProvider) {
            ServiceProvider serviceProvider = (ServiceProvider) user;
            // Assuming ServiceProvider has a method to submit the request
//            serviceProvider.submitRequest(price, readyDate); // Adjust method name accordingly
        }
        System.out.println("Request submitted with Price: " + price + ", Ready Date: " + readyDate);
    }

    private void clearFields() {
        setPriceField.clear();
        readyDateField.clear();
    }

    private void showFeedback(String message, String type) {
        feedbackMessage.setText(message);
        feedbackMessage.getStyleClass().removeAll("success", "error");
        feedbackMessage.getStyleClass().add(type);
    }

    public void setUser(User user) {
        this.user = user;  // Set the user reference
    }
}
