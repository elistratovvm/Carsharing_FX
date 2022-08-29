module com.example.carsharing_fx {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires java.naming;
    requires org.postgresql.jdbc;


    opens application to javafx.fxml;
    exports application;
    exports service.database;
    opens service.database to javafx.fxml;
}