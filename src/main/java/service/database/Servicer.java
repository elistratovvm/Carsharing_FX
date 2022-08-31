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

            int lastIntValue;

            if (resultSet.next()) {

                resultSet.last();
                String lastValue = resultSet.getString(1);
                lastIntValue = Integer.parseInt(lastValue);
                lastIntValue++;
            } else {

                lastIntValue = 1;
            }

            return String.valueOf(lastIntValue);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
