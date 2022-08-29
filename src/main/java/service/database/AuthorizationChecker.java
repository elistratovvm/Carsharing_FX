package service.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AuthorizationChecker {

    Connection connection;

    public AuthorizationChecker(Connection connection) {
        this.connection = connection;
    }

    // Check Aggregator
    public boolean checkAggregatorAuthorization(String login) {

        try {
            String query = "SELECT id "
                    + "FROM aggregators "
                    + "WHERE id = '" + login + "'";

            Statement statement = new DatabaseConnector().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            return resultSet.next();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return false;
    }

    // Check Customer
    public boolean checkCustomerAuthorization(String login) {

        try {
            String query = "SELECT drivers_license "
                    + "FROM customers "
                    + "WHERE drivers_license = '" + login + "'";

            Statement statement = new DatabaseConnector().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            return resultSet.next();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return false;
    }
}
