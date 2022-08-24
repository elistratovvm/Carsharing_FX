package application;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddValues {

	// Create contract
	public void addContract(String plate, String login) {

		try {
			String lastIdContract = getLastValueId("SELECT id FROM contracts");
			String ctrQuery = "INSERT INTO contracts (id, agr_id, ctr_id) "
					+ "VALUES ('" + lastIdContract + "', 'a01', '" + login + "')";
			String cttQuery = "INSERT INTO contract_details (ctt_id, car_id, contract_date) "
					+ "VALUES ('" + lastIdContract + "', '" + getCarIdFromPlate(plate) + "', SYSDATE)";

			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
			PreparedStatement preparedStatement1 = conn.prepareStatement(ctrQuery);
			PreparedStatement preparedStatement2 = conn.prepareStatement(cttQuery);

			preparedStatement1.execute();
			preparedStatement2.execute();

			preparedStatement1.close();
			preparedStatement2.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Registration Customer
	public void addCustomer(String firstName, String lastName, String license) {

		try {
			String lastIdCustomer = getLastValueId("SELECT id FROM customers");
			String query = "INSERT INTO customers (id, first_name, last_name, drivers_license) "
					+ "VALUES ('" + lastIdCustomer + "', '" + firstName + "', '" + lastName + "', '" + license + "')";

			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(query);

			preparedStatement.execute();

			preparedStatement.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Add Car
	public void addCar(String point, String car, String plate) {

		try {
			String lastIdCar = getLastValueId("SELECT id FROM cars");
			String query1 = "INSERT INTO cars (id, license_plate, technical_condition, fuel, cte_brand_and_model) "
					+ "VALUES ('" + lastIdCar + "', '" + plate + "', 'Отличное', 100, '" + car + "'";
			String query2 = "INSERT INTO car_lease_point_details (car_id, lpt_id)"
					+ "VALUES ('" + lastIdCar + "', '" + getIdPoint(point) + "')";

			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
			PreparedStatement preparedStatement1 = conn.prepareStatement(query1);
			PreparedStatement preparedStatement2 = conn.prepareStatement(query2);

			preparedStatement1.execute();
			preparedStatement2.execute();

			preparedStatement1.close();
			preparedStatement2.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Add Point
	public void addPoint(String address, String x, String y) {

		try {
			String lastIdPoint = getLastValueId("SELECT id FROM lease_points");
			String query = "INSERT INTO lease_points (id, address, x_coordinate, y_coordinate, agr_id) "
					+ "VALUES ('" + lastIdPoint + "', '" + address + "', " + x + ", " + y + ", 'a01')";

			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(query);

			preparedStatement.execute();

			preparedStatement.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Get Point Id from Address
	private String getIdPoint(String point) {

		try {
			String query = "SELECT id FROM lease_points WHERE address = '" + point + "'";

			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
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

	//Get Car ID from Plate
	private String getCarIdFromPlate(String plate) {

		try {
			String query = "SELECT id FROM cars WHERE license_plate = '" + plate + "'";

			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
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

	//Get last Value ID + 1
	private String getLastValueId(String query) {

		try {
			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(query,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = preparedStatement.executeQuery(query);
			resultSet.next();
			resultSet.last();
			
			String lastValue = resultSet.getString(1);
			int lastIntValue = Integer.parseInt(lastValue.substring(1));
			lastIntValue++;
			String newLastValue = lastValue.substring(0, 1);
			
			for (int i = 1; i < lastValue.length() - String.valueOf(lastIntValue).length(); i++) {
				newLastValue += "0";
			}
			newLastValue += lastIntValue;
			
			return newLastValue;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
}