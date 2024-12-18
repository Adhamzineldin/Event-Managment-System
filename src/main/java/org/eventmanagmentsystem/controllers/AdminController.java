package org.eventmanagmentsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.eventmanagmentsystem.models.*;
import org.eventmanagmentsystem.services.AdminService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private Label adminName;

    @FXML
    private Label adminEmail;

    @FXML
    private Label adminRole;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> colUserName;

    @FXML
    private TableColumn<User, String> colRole;

    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button sendMessageButton;

    private AdminService adminService;
    private User user;  // Store the logged-in user

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adminService = new AdminService();
        roleComboBox.getItems().addAll("Admin", "Customer", "ServiceProvider");
        configureTableView();
        loadUsers();
    }

    public void setUser(User user) {
        this.user = user;
        adminName.setText(user.getUserName());
        adminEmail.setText(user.getEmail());
        adminRole.setText(user.getRole());
    }

    private void configureTableView() {
        colUserName.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());
        colRole.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
    }

    private void loadUsers() {
        List<User> users = adminService.getAllUsers();
        userTable.getItems().setAll(users);
    }

    @FXML
    public void handleLogout(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginPage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) adminName.getScene().getWindow();
        double previousWidth = stage.getWidth();
        double previousHeight = stage.getHeight();
        stage.setScene(scene);
        stage.setWidth(previousWidth);
        stage.setHeight(previousHeight);
        stage.show();
    }

    @FXML
    public void handleAddUser(ActionEvent event) {  // Correct parameter to ActionEvent
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String role = roleComboBox.getValue();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || role == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill all fields.");
            return;
        }

        User newUser = new User(0, username, password, email, role);
        boolean added = adminService.addUser(newUser);

        if (added) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "User added successfully.");
            loadUsers();  // Reload the user list
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add user.");
        }
    }



    @FXML
    public void handleRemoveUser(ActionEvent event) {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a user to remove.");
            return;
        }

        boolean removed = adminService.removeUser(selectedUser.getId(), selectedUser.getRole());

        if (removed) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "User removed successfully.");
            loadUsers();  // Reload the user list
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to remove user.");
        }
    }

    @FXML
    public void handleSendMessage(ActionEvent event) {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a user to send a message.");
            return;
        }

        TextInputDialog messageDialog = new TextInputDialog();
        messageDialog.setTitle("Send Message");
        messageDialog.setHeaderText("Send a message to " + selectedUser.getUserName());
        messageDialog.setContentText("Enter your message:");

        messageDialog.showAndWait().ifPresent(message -> {
            boolean sent = user.sendMessage(selectedUser, message);
            if (sent) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Message sent successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to send message.");
            }
        });
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
