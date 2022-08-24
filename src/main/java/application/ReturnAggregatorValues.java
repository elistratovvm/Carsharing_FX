package application;

import oracle.jdbc.pool.OracleDataSource;

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

			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
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

			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
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
