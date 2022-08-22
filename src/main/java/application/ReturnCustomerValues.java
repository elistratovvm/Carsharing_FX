package application;
	//task completed by Elistratov Vitaliy

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ReturnCustomerValues {
	public String returnCustomerId(String customerPlate) {		//Return Customer ID 
		try {
			String query = "SELECT id"
					+ " FROM customers "
					+ "WHERE drivers_license = '" + customerPlate + "'";
			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query, 
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = pstmt.executeQuery(query);
			rset.next();
			return rset.getString(1);
		} 
		
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public ArrayList<String> returnCarOnPointList(String nearestPointAddress) {	//Return Car On Point List 
		ArrayList<String> carList = new ArrayList<String>();
		try {
			String query = "SELECT c.license_plate, c.cte_brand_and_model, "
					+ "c.technical_condition, c.fuel, ct.rate_in_hour "
					+ "FROM cars c JOIN car_lease_point_details d "
					+ "ON (c.id = d.car_id) "
					+ "JOIN car_types ct "
					+ "ON (c.cte_brand_and_model = ct.brand_and_model) "
					+ "WHERE ((d.lpt_id = '" + getNearesPointId(nearestPointAddress) + "') "
					+ "AND (c.id NOT IN(SELECT car_id "
					+ "FROM contract_details "
					+ "WHERE contract_date != SYSDATE)))";
			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query, 
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = pstmt.executeQuery(query);
			
			while (rset.next()) {
				carList.add("Plate: " + rset.getString(1) + 
						", " + rset.getString(2) + 
						", Technical condition: " + rset.getString(3) +
						", Fuel: " + rset.getString(4) +
						", Rate: " + rset.getString(5));
			}
			return carList;
		} 
		
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return carList;
	}
	public ArrayList<Integer> returnPointCoordinateList() {		//Return Point Coordinate List
		ArrayList<Integer> coordinateList = new ArrayList<Integer>();
		try {
			String query = "SELECT x_coordinate, y_coordinate "
					+ " FROM lease_points";
			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query, 
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = pstmt.executeQuery(query);
			
			while (rset.next()) {
				coordinateList.add(Integer.parseInt(rset.getString(1)));
				coordinateList.add(Integer.parseInt(rset.getString(2)));
			}
			return coordinateList;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return coordinateList;
	}
	public String getNearesPointAddress(int pointNumber) {			//Get Address nearest Point from ID
		try {
			String query = "SELECT address "
					+ " FROM lease_points "
					+ "WHERE id = '" + getValue(pointNumber)+ "'";
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
	public String getNearesPointId(String nearestPointAddress) {		//Get ID nearest Point from Address
		try {
			String query = "SELECT id address "
					+ " FROM lease_points "
					+ "WHERE address = '" + nearestPointAddress + "'";
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
	private String getValue(int pointNumber) {		//get ID Point from number
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