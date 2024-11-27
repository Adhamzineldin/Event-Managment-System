package org.eventmanagmentsystem.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIService {

    public void changeScene(String fxmlPath, Node node) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) node.getScene().getWindow();
        double previousWidth = stage.getWidth();
        double previousHeight = stage.getHeight();
        stage.setScene(scene);
        stage.setWidth(previousWidth);
        stage.setHeight(previousHeight);
        stage.show();


    }


}
