package org.eventmanagmentsystem.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.eventmanagmentsystem.factories.UserFactory;
import org.eventmanagmentsystem.models.Admin;
import org.eventmanagmentsystem.models.User;

import java.util.List;

public class AdminController {

    private Admin admin;

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> userRoleComboBox;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField; // Password input for new users

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private Label statusLabel;

    public void initialize() {
        admin = new Admin(1, "admin", "admin", "admin@admin.com", "admin");

        // Setup ComboBox for roles
        userRoleComboBox.setItems(FXCollections.observableArrayList("Admin", "Manager", "ServiceProvider", "Customer"));

        // Setup table columns correctly using the correct getter methods
        usernameColumn.setCellValueFactory(cellData -> {
            // Wrap the username in a SimpleStringProperty
            return new SimpleStringProperty(cellData.getValue().getUserName());
        });

        roleColumn.setCellValueFactory(cellData -> {
            // Wrap the role in a SimpleStringProperty
            return new SimpleStringProperty(cellData.getValue().getRole());
        });

        loadUsers();
    }


    public void loadUsers() {
        List<User> users = admin.getAllUsers();
        ObservableList<User> observableUsers = FXCollections.observableArrayList(users);
        userTableView.setItems(observableUsers);
        for (User user : users) {
            System.out.println(user.getUserName());
        }
    }

    @FXML
    public void addUser() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText(); // Get password
        String role = userRoleComboBox.getValue();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || role == null) {
            statusLabel.setText("Please fill in all fields.");
            return;
        }

        User user = UserFactory.createUser(0, username, password, email, role); // Create user with password
        if (admin.addUser(user)) {
            statusLabel.setText("User added successfully.");
            loadUsers(); // Refresh the table
        } else {
            statusLabel.setText("Failed to add user.");
        }
    }

    @FXML
    public void removeUser() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            statusLabel.setText("Please select a user to remove.");
            return;
        }

        if (admin.removeUser(selectedUser.getId(), selectedUser.getRole())) {
            statusLabel.setText("User removed successfully.");
            loadUsers(); // Refresh the table
        } else {
            statusLabel.setText("Failed to remove user.");
        }
    }

    public void setUser(User user) {
        this.admin = (Admin) user;
    }
}
