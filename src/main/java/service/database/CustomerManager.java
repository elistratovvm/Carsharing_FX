package service.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomerManager {

	private final Connection connection;
	private final Servicer servicer;

	private String customerID;
	private String customerName;


	public CustomerManager(Connection connection, String customerPlate) {

		this.connection = connection;
		this.servicer = new Servicer(connection);

		String[] customerInfo = getCustomerInfo(customerPlate);
		this.setCustomerID(customerInfo[0]);
		this.setCustomerName(customerInfo[1]);
	}

	// Get Customer info
	public String[] getCustomerInfo(String customerPlate) {

		try {
			String query = "SELECT id, first_name, last_name " +
					"FROM public.customers " +
					"WHERE drivers_license = '" + customerPlate + "'";

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			resultSet.next();

			return new String[]{
					resultSet.getString(1),
					resultSet.getString(2) + " " + resultSet.getString(3)
			};
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	// Create contract
	public void createContract(String plate, String login) {

		try {
			String lastIdContract = servicer.getLastValueId("SELECT id FROM contracts");
			String ctrQuery = "INSERT INTO public.contracts (id, agr_id, ctr_id) " +
					"VALUES (" + lastIdContract + ", 1, " + login + ")";
			String cttQuery = "INSERT INTO public.contract_details (ctt_id, car_id, contract_date) " +
					"VALUES (" + lastIdContract + ", " + new CarGetter(connection).getCarIdFromPlate(plate) +
					", CURRENT_DATE)";

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

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}