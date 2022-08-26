package application;

import service.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.Statement;

public class CustomerAuthorization {

	// Check Customer
	public boolean checkCustomerAuthorization(String login) {

		try {
			String query = "SELECT drivers_license "
					+ "FROM customers "
					+ "WHERE drivers_license = '" + login + "'";

			Class.forName("org.postgresql.Driver");
			Statement statement = new DatabaseConnector().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			return resultSet.next();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return false;
	}
}
