package application;

import service.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ReturnAggregatorValues {

	// Return Point Address List
	public ArrayList<String> returnPointAddressList() {

		ArrayList<String> idList = new ArrayList<>();
		try {
			String query = "SELECT address"
					+ " FROM lease_points";

			Connection conn = new DatabaseConnector().getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(query,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = preparedStatement.executeQuery(query);
			
			while (resultSet.next()) {
				idList.add(resultSet.getString(1));
			}

			return idList;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return idList;
	}

	// Return Car List
	public ArrayList<String> returnCarList() {

		ArrayList<String> idList = new ArrayList<>();
		try {
			String query = "SELECT brand_and_model"
					+ " FROM car_types";

			Class.forName("org.postgresql.Driver");
			Connection conn = new DatabaseConnector().getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(query,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = preparedStatement.executeQuery(query);
			
			while (resultSet.next()) {
				idList.add(resultSet.getString(1));
			}
			
			return idList;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return idList;
	}
}
