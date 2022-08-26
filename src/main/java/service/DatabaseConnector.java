package service;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {

    private Connection connection;

    public DatabaseConnector() {

        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/carshering", "postgres", "postgres");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

}
