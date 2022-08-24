package application;

import service.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AggregatorAuthorization {

	// Check Aggregator
	public boolean checkAggregatorAuthorization(String login) {

		try {
			String query = "SELECT id "
					+ "FROM aggregators "
					+ "WHERE id = '" + login + "'";

			Connection conn = new DatabaseConnector().getConn();
			PreparedStatement preparedStatement = conn.prepareStatement(query,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = preparedStatement.executeQuery(query);

			return resultSet.next();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return false;
	}
}
