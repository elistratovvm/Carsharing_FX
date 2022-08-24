package service;

import oracle.jdbc.pool.OracleDataSource;
import java.sql.Connection;

public class DatabaseConnector {

    private static OracleDataSource ods;
    private Connection conn;

    public DatabaseConnector() {

        try {
            OracleDataSource ods = new OracleDataSource();
            ods.setURL("jdbc:oracle:thin:carsharing/carsharing@localhost:1521/xe");
            Connection conn = ods.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        return conn;
    }

}
