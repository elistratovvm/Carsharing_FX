package service.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Servicer {

    Connection connection;

    public Servicer(Connection connection) {
        this.connection = connection;
    }

    //Get last Value ID + 1
    public String getLastValueId(String query) {

        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(query);

            resultSet.next();
            resultSet.last();

            String lastValue = resultSet.getString(1);
            int lastIntValue = Integer.parseInt(lastValue.substring(1));
            lastIntValue++;

            return lastValue.charAt(0) + "0".repeat(Math.max(0, lastValue.length() - String.valueOf(lastIntValue).length() - 1)) +
                    lastIntValue;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
