module com.example.carsharing_fx {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires ojdbc8;
    requires java.sql;
    requires java.desktop;
    requires java.naming;


    opens com.example.carsharing_fx to javafx.fxml;
    exports application;
}