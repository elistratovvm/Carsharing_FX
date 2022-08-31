package service.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {

    private Connection connection;

    public DatabaseConnector(String url, String user, String password) {

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

}
