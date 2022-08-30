package service.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CarGetter {

    Connection connection;

    public CarGetter(Connection connection) {
        this.connection = connection;
    }

    // Get Car ID from Plate
    public String getCarIdFromPlate(String plate) {

        try {
            String query = "SELECT id FROM cars WHERE license_plate = '" + plate + "'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            resultSet.next();

            return resultSet.getString(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    // Get Car List
    public ArrayList<String> getCarList() {

        ArrayList<String> idList = new ArrayList<>();
        try {
            String query = "SELECT brand_and_model"
                    + " FROM car_types";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                idList.add(resultSet.getString(1));
            }

            return idList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return idList;
    }

    // Get Car On Point List
    public ArrayList<String> getCarOnPointList(String nearestPointAddress) {

        ArrayList<String> carList = new ArrayList<>();
        try {
            String query = "SELECT c.license_plate, c.cte_brand_and_model, "
                    + "c.technical_condition, c.fuel, ct.rate_in_hour "
                    + "FROM cars c JOIN car_lease_point_details d "
                    + "ON (c.id = d.car_id) "
                    + "JOIN car_types ct "
                    + "ON (c.cte_brand_and_model = ct.brand_and_model) "
                    + "WHERE ((d.lpt_id = '" + new PointManager(connection).getNearestPointId(nearestPointAddress) + "') "
                    + "AND (c.id NOT IN(SELECT car_id "
                    + "FROM contract_details "
                    + "WHERE contract_date != CURRENT_DATE)))";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                carList.add("Plate: " + resultSet.getString(1) +
                        ", " + resultSet.getString(2) +
                        ", Technical condition: " + resultSet.getString(3) +
                        ", Fuel: " + resultSet.getString(4) +
                        ", Rate: " + resultSet.getString(5));
            }

            return carList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return carList;
    }
}
