package application;
	//task completed by Elistratov Vitaliy

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerAuthorization {
	public boolean checkCustomerAuthorization(String login) {	//Check Customer
		try {
			/**������� �� ���������� ��**/	//test: 	123456789123
		String query = "SELECT drivers_license "
				+ "FROM customers "
				+ "WHERE drivers_license = '" + login + "'";
		OracleDataSource ods = new OracleDataSource();
		ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
		Connection conn = ods.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query, 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet rset = pstmt.executeQuery(query);
			if(rset.next()) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return false;
	}
}
