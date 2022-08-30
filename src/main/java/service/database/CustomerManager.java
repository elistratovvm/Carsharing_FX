package service.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomerManager {

	Connection connection;
	Servicer servicer;
	PointManager pointManager;

	public CustomerManager(Connection connection) {

		this.connection = connection;
		this.servicer = new Servicer(connection);
		this.pointManager = new PointManager(connection);
	}

	// Get Customer ID
	public String getCustomerID(String customerPlate) {

		try {
			String query = "SELECT id"
					+ " FROM customers "
					+ "WHERE drivers_license = '" + customerPlate + "'";

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			resultSet.next();

			return resultSet.getString(1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	// Create contract
	public void createContract(String plate, String login) {

		try {
			String lastIdContract = servicer.getLastValueId("SELECT id FROM contracts");
			String ctrQuery = "INSERT INTO contracts (id, agr_id, ctr_id) "
					+ "VALUES ('" + lastIdContract + "', 'a01', '" + login + "')";
			String cttQuery = "INSERT INTO contract_details (ctt_id, car_id, contract_date) "
					+ "VALUES ('" + lastIdContract + "', '" + new CarGetter(connection).getCarIdFromPlate(plate)
					+ "', CURRENT_DATE)";

			Statement statement1 = connection.createStatement();
			Statement statement2 = connection.createStatement();

			statement1.execute(ctrQuery);
			statement2.execute(cttQuery);

			statement1.close();
			statement2.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}