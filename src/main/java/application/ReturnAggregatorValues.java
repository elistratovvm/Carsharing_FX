package application;
	//task completed by Elistratov Vitaliy

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ReturnAggregatorValues {
	public ArrayList<String> returnPointAddressList() {			//Return Point Address List
		ArrayList<String> idList = new ArrayList<String>();
		try {
			String query = "SELECT address"
					+ " FROM lease_points";
			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query, 
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = pstmt.executeQuery(query);
			
			while (rset.next()) {
				idList.add(rset.getString(1));
			}
			return idList;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return idList;
	}
	
	public ArrayList<String> returnCarList() {					//Return Car List
		ArrayList<String> idList = new ArrayList<String>();
		try {
			String query = "SELECT brand_and_model"
					+ " FROM car_types";
			OracleDataSource ods = new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
			Connection conn = ods.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query, 
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = pstmt.executeQuery(query);
			
			while (rset.next()) {
				idList.add(rset.getString(1));
			}
			
			return idList;
		} 
		
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return idList;
	}
}
