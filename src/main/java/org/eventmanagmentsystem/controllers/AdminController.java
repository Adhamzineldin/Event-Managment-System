package org.eventmanagmentsystem.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.eventmanagmentsystem.factories.UserFactory;
import org.eventmanagmentsystem.models.Admin;
import org.eventmanagmentsystem.models.User;

import java.io.IOException;
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
    private TableColumn<User, String> idColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

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
    private Button signOutButton; // Added Sign-out Button

    @FXML
    private Button searchButton; // Added Search Button

    @FXML
    private Label statusLabel;

    @FXML
    private Button saveButton; // Save Changes Button

    public void initialize() {
        admin = new Admin(1, "admin", "admin", "admin@admin.com", "admin");

        // Setup ComboBox for roles
        userRoleComboBox.setItems(FXCollections.observableArrayList("Admin", "Manager", "ServiceProvider", "Customer"));

        // Setup table columns correctly using the correct getter methods
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserName()));
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        passwordColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
        roleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole()));

        // Load users after setting the columns
        loadUsers();

        // Set a listener to open the edit dialog when a row is selected
        userTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Open a dialog or populate fields for editing user data
                openEditUserDialog(newValue);
            }
        });
    }

    public void loadUsers() {
        List<User> users = admin.getAllUsers();
        ObservableList<User> observableUsers = FXCollections.observableArrayList(users);
        userTableView.setItems(observableUsers);
        userTableView.refresh();
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

    @FXML
    public void signOut() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/LoginPage.fxml"));
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

    @FXML
    public void searchUsers() {
        String searchQuery = searchField.getText().toLowerCase();
        ObservableList<User> filteredUsers = FXCollections.observableArrayList();

        for (User user : admin.getAllUsers()) {
            if (user.getUserName().toLowerCase().contains(searchQuery) ||
                    user.getEmail().toLowerCase().contains(searchQuery) ||
                    user.getRole().toLowerCase().contains(searchQuery)) {
                filteredUsers.add(user);
            }
        }

        userTableView.setItems(filteredUsers);
    }

    private void openEditUserDialog(User user) {
        // Populate the fields with user data
        usernameField.setText(user.getUserName());
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
        userRoleComboBox.setValue(user.getRole());

        // Enable Save button after editing
        saveButton.setVisible(true);
        saveButton.setOnAction(event -> saveUserChanges(user));
    }

    @FXML
    public void saveUserChanges(User user) {
        // Save the updated user data
        user.setUserName(usernameField.getText());
        user.setEmail(emailField.getText());
        user.setPassword(passwordField.getText());
        user.setRole(userRoleComboBox.getValue());

        // Update the user data
        if (admin.updateUser(user)) {
            statusLabel.setText("User data updated successfully.");
            loadUsers(); // Refresh the table
        } else {
            statusLabel.setText("Failed to update user data.");
        }

        // Hide save button after saving changes
        saveButton.setVisible(false);
    }

    public void setUser(User user) {
        this.admin = (Admin) user;
    }

    public void saveUserChanges(ActionEvent actionEvent) {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            statusLabel.setText("Please select a user to remove.");
            return;
        }

        selectedUser.setUserName(usernameField.getText());
        selectedUser.setEmail(emailField.getText());
        selectedUser.setPassword(passwordField.getText());
        selectedUser.setRole(userRoleComboBox.getValue());

        // Update the user data
        if (admin.updateUser(selectedUser)) {
            statusLabel.setText("User data updated successfully.");
            loadUsers(); // Refresh the table
        } else {
            statusLabel.setText("Failed to update user data.");
        }

        // Hide save button after saving changes
        saveButton.setVisible(false);
    }
}
