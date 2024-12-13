package org.eventmanagmentsystem.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class Main extends Application {
     @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginPage.fxml"));
        Image icon = new Image(getClass().getResourceAsStream("/images/icon.png")); // Path to the icon image
        primaryStage.getIcons().add(icon);

        // Set the scene with the loaded FXML
        Scene scene = new Scene(loader.load(), 800, 600);

        // Configure the stage
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
