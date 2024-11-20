module org.example.eventmanagmentsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    exports org.eventmanagmentsystem.app;
    opens org.eventmanagmentsystem.app to javafx.fxml;
    exports org.eventmanagmentsystem.controllers;
    opens org.eventmanagmentsystem.controllers to javafx.fxml;
    exports org.eventmanagmentsystem.views; // Make the views package accessible
    opens org.eventmanagmentsystem.views to javafx.fxml;
}
