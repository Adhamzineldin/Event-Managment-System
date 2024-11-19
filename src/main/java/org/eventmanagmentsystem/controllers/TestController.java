package org.eventmanagmentsystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TestController {

    @FXML
    private Label welcomeText; // Bind this to the Label in the FXML file

    @FXML
    private void handleButtonClick() {
        // Check the current text of the label and toggle it
        if (welcomeText.getText() == null || welcomeText.getText().isEmpty()) {
            welcomeText.setText("Welcome to JavaFX Application!");
            System.out.println("Text added: Welcome to JavaFX Application!");
        } else {
            welcomeText.setText("");
            System.out.println("Text removed!");
        }
    }

}
