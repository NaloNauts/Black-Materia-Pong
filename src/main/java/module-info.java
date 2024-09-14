module com.example.java_pong {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.java_pong.model; // Opens the model package to other modules
    opens com.example.java_pong.controller; // Opens the controller package to other modules
    opens com.example.java_pong.view; // Opens the controller package to other modules






    exports com.example.java_pong;
}
