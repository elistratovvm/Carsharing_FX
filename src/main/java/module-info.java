module com.example.carsharing_fx {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires ojdbc8;
    requires java.sql;
    requires java.desktop;
    requires java.naming;


    opens application to javafx.fxml;
    exports application;
}