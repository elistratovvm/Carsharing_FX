package application;

import service.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ReturnAggregatorValues {

	// Return Point Address List
	public ArrayList<String> returnPointAddressList() {

		ArrayList<String> idList = new ArrayList<>();
		try {
			String query = "SELECT address"
					+ " FROM lease_points";

			Statement statement = new DatabaseConnector().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
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

			Statement statement = new DatabaseConnector().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
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
