package service.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AuthorizationManager {

    Connection connection;

    public AuthorizationManager(Connection connection) {
        this.connection = connection;
    }

    // Check aggregator
    public boolean checkAggregatorAuthorization(String login) {

        try {
            String query = "SELECT id "
                    + "FROM public.aggregators "
                    + "WHERE id = '" + login + "'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            return resultSet.next();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    // Check customer
    public boolean checkCustomerAuthorization(String login) {

        try {
            String query = "SELECT drivers_license "
                    + "FROM public.customers "
                    + "WHERE drivers_license = '" + login + "'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            return resultSet.next();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    // Create new customer
    public void createCustomer(String firstName, String lastName, String license) {

        try {
            String lastIdCustomer = new Servicer(connection).getLastValueId("SELECT id FROM public.customers");
            String query = "INSERT INTO public.customers (id, first_name, last_name, drivers_license) "
                    + "VALUES ('" + lastIdCustomer + "', '" + firstName + "', '" + lastName + "', '" + license + "')";

            Statement statement = connection.createStatement();

            statement.execute(query);

            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}