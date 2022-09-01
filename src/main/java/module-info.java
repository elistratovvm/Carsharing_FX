module com.example.carsharing_fx {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires java.naming;
    requires org.postgresql.jdbc;

    exports application.windows;
    opens application.windows to javafx.fxml;
    exports service.database;
    opens service.database to javafx.fxml;
}