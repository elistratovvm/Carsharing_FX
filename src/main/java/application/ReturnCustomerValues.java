package application;

import service.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ReturnCustomerValues {

	// Return Customer ID
	public String returnCustomerId(String customerPlate) {

		try {
			String query = "SELECT id"
					+ " FROM customers "
					+ "WHERE drivers_license = '" + customerPlate + "'";

			Statement statement = new DatabaseConnector().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			resultSet.next();

			return resultSet.getString(1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	// Return Car On Point List
	public ArrayList<String> returnCarOnPointList(String nearestPointAddress) {

		ArrayList<String> carList = new ArrayList<>();
		try {
			String query = "SELECT c.license_plate, c.cte_brand_and_model, "
					+ "c.technical_condition, c.fuel, ct.rate_in_hour "
					+ "FROM cars c JOIN car_lease_point_details d "
					+ "ON (c.id = d.car_id) "
					+ "JOIN car_types ct "
					+ "ON (c.cte_brand_and_model = ct.brand_and_model) "
					+ "WHERE ((d.lpt_id = '" + getNearestPointId(nearestPointAddress) + "') "
					+ "AND (c.id NOT IN(SELECT car_id "
					+ "FROM contract_details "
					+ "WHERE contract_date != SYSDATE)))";

			Connection conn = new DatabaseConnector().getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(query,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = preparedStatement.executeQuery(query);
			
			while (resultSet.next()) {
				carList.add("Plate: " + resultSet.getString(1) +
						", " + resultSet.getString(2) +
						", Technical condition: " + resultSet.getString(3) +
						", Fuel: " + resultSet.getString(4) +
						", Rate: " + resultSet.getString(5));
			}

			return carList;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return carList;
	}

	// Return Point Coordinate List
	public ArrayList<Integer> returnPointCoordinateList() {

		ArrayList<Integer> coordinateList = new ArrayList<>();
		try {
			String query = "SELECT x_coordinate, y_coordinate "
					+ " FROM lease_points";

			Statement statement = new DatabaseConnector().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while (resultSet.next()) {
				coordinateList.add(Integer.parseInt(resultSet.getString(1)));
				coordinateList.add(Integer.parseInt(resultSet.getString(2)));
			}

			return coordinateList;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return coordinateList;
	}

	// Get Address the nearest Point from ID
	public String getNearestPointAddress(int pointNumber) {

		try {
			String query = "SELECT address "
					+ " FROM lease_points "
					+ "WHERE id = '" + getValue(pointNumber)+ "'";

			Class.forName("org.postgresql.Driver");
			Connection conn = new DatabaseConnector().getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(query,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = preparedStatement.executeQuery(query);

			resultSet.next();
			
			return resultSet.getString(1);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	// Get ID nearest Point from Address
	public String getNearestPointId(String nearestPointAddress) {

		try {
			String query = "SELECT id address "
					+ " FROM lease_points "
					+ "WHERE address = '" + nearestPointAddress + "'";

			Connection conn = new DatabaseConnector().getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(query,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = preparedStatement.executeQuery(query);

			resultSet.next();
			
			return resultSet.getString(1);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	// Get ID Point from number
	private String getValue(int pointNumber) {

		try {
			String nearestPointId = "p";
			
			for (int i = 1; i < 5 - String.valueOf(pointNumber).length(); i++) {
				nearestPointId += "0";
			}
			nearestPointId += pointNumber;
			
			return nearestPointId;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
}