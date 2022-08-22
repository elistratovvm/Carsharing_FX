package application;
	//task completed by Elistratov Vitaliy

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddValues {
	public void addContract(String plate, String login) {	//Create contract
		try {
			String lastId = getLastValueId("SELECT id FROM contracts");
			String query1 = "INSERT INTO contracts (id, agr_id, ctr_id) "
					+ "VALUES ('" + lastId + "', 'a01', '" + login + "')";
			String query2 = "INSERT INTO contract_details (ctt_id, car_id, contract_date) "
					+ "VALUES ('" + lastId + "', '" + getCarIdFromPlate(plate) + "', " + "SYSDATE)";
			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement(query1);
			PreparedStatement pstmt2 = conn.prepareStatement(query2);
			
			pstmt1.execute();
			pstmt2.execute();
			
			pstmt1.close();
			pstmt2.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public void addCustomer(String fname, String lname, String license) {	//Registration Customer
		try {
			String lastId = getLastValueId("SELECT id FROM customers");
			String query = "INSERT INTO customers (id, first_name, last_name, drivers_license) "
					+ "VALUES ('" + lastId + "', '" + fname + "', '" + lname + "', '" + license + "')";
			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			
			pstmt.execute();
			
			pstmt.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public void addCar(String point, String car, String plate) {		//Add Car
		try {
			String lastId = getLastValueId("SELECT id FROM cars");
			String query1 = "INSERT INTO cars (id, license_plate, technical_condition, fuel, cte_brand_and_model) "
					+ "VALUES ('" + lastId + "', '" + plate + "', '��������', 100, 'Skoda Rapid')";
			String query2 = "INSERT INTO car_lease_point_details (car_id, lpt_id)"
					+ "VALUES ('" + lastId + "', '" + getIdPoint(point) + "')";
			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement(query1);
			PreparedStatement pstmt2 = conn.prepareStatement(query2);
			
			pstmt1.execute();
			pstmt2.execute();
			
			pstmt1.close();
			pstmt2.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public void addPoint(String address, String x, String y) {		//Add Point
		try {
			String lastId = getLastValueId("SELECT id FROM lease_points");
			String query = "INSERT INTO lease_points (id, address, x_coordinate, y_coordinate, agr_id) "
					+ "VALUES ('" + lastId + "', '" + address + "', " + x + ", " + y + ", 'a01')";
			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.execute();
			pstmt.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	private String getIdPoint(String point) {		//Get Point Id from Address
		try {
			String query = "SELECT id FROM lease_points WHERE address = '" + point + "'";
			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = pstmt.executeQuery(query);
			rset.next();
			return rset.getString(1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	private String getCarIdFromPlate(String plate) {			//Get Car ID from Plate
		try {
			String query = "SELECT id FROM cars WHERE license_plate = '" + plate + "'";
			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = pstmt.executeQuery(query);
			rset.next();
			return rset.getString(1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	private String getLastValueId(String query) {		//Get last Value ID + 1
		try {
			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = pstmt.executeQuery(query);
			rset.next();
			rset.last();
			
			String lastValue = rset.getString(1);
			int lastIntValue = Integer.parseInt(lastValue.substring(1, lastValue.length()));
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