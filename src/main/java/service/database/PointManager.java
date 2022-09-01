package service.database;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PointManager {

    Connection connection;

    public PointManager(Connection connection) {
        this.connection = connection;
    }

    // Get Location Point
    public Circle getCustomerPoint() {

        ArrayList<Integer> pointCoordinates;
        pointCoordinates = getPointCoordinateList();

        int pointQuantity = pointCoordinates.size()/2 ;

        Circle circle = new Circle(
                pointCoordinates.get((pointQuantity - 1)*2) + 25,
                pointCoordinates.get((pointQuantity - 1)*2 + 1) + 25,
                6);
        circle.setStroke(Color.WHITE);

        return circle;
    }

    // Set location Point
    public void setCustomerPoint(ImageView map, Circle customerPoint) {

        map.setOnMousePressed(me -> {
            customerPoint.setCenterX(me.getX() + 25);
            customerPoint.setCenterY(me.getY() + 25);
        });
    }

    // Get Point id from Address
    public String getPointIDFromAddress(String point) {

        try {
            String query = "SELECT id " +
                    "FROM public.lease_points " +
                    "WHERE address = '" + point + "'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            resultSet.next();

            return resultSet.getString(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    // Get ID nearest Point from Address
    // !Copy functionality of getPointIDFromAddress method  !
    // !Delete in future update                             !
    public String getNearestPointId(String nearestPointAddress) {

        try {
            String query = "SELECT id " +
                    "FROM public.lease_points " +
                    "WHERE address = '" + nearestPointAddress + "'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            resultSet.next();

            return resultSet.getString(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    // Get Address the nearest Point from ID
    public String getNearestPointAddress(int pointNumber) {

        try {
            String query = "SELECT address " +
                    "FROM public.lease_points " +
                    "WHERE id = '" + pointNumber + "'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            resultSet.next();

            return resultSet.getString(1);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    // Get Point Address List
    public ArrayList<String> getPointAddressList() {

        ArrayList<String> idList = new ArrayList<>();
        try {
            String query = "SELECT address " +
                    "FROM public.lease_points";

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

    // Get Point Coordinate List
    public ArrayList<Integer> getPointCoordinateList() {

        ArrayList<Integer> coordinateList = new ArrayList<>();
        try {
            String query = "SELECT x_coordinate, y_coordinate " +
                    "FROM public.lease_points";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                coordinateList.add(Integer.parseInt(resultSet.getString(1)));
                coordinateList.add(Integer.parseInt(resultSet.getString(2)));
            }

            return coordinateList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return coordinateList;
    }

    // Create Points
    public Circle[] paintPoint() {

        ArrayList<Integer> pointCoordinates;
        pointCoordinates = getPointCoordinateList();

        int pointQuantity = pointCoordinates.size()/2 ;
        int i;

        Circle[] circles = new Circle[pointQuantity + 1];
        for (i = 0; i < pointQuantity; i++) {
            circles[i] = new Circle(
                    pointCoordinates.get(i*2) + 25,
                    pointCoordinates.get(i*2 + 1) + 25,
                    6);
            circles[i].setStroke(Color.WHITE);
        }

        circles[i] = new Circle (
                pointCoordinates.get((i - 1)*2) + 25,
                pointCoordinates.get((i - 1)*2 + 1) + 25,
                6);

        return circles;
    }

    //Calculate the nearest Point
    public String calculateNearestPoint(
            String x,
            String y,
            ArrayList<Integer> pointCoordinates) {

        double xInt = Double.parseDouble(x);
        double yInt = Double.parseDouble(y);
        int pointQuantity = pointCoordinates.size()/2;
        double min = 300;
        int pointNumber = -1;

        double[] length = new double[pointQuantity];
        for (int i = 0; i < pointQuantity; i++) {
            length[i] = Math.sqrt(
                    ((pointCoordinates.get(i*2) - xInt)*(pointCoordinates.get(i*2) - xInt))
                            + ((pointCoordinates.get(i*2 + 1) - yInt)*(pointCoordinates.get(i*2 + 1) - yInt))
            );
        }

        for (int i = 0; i < pointQuantity; i++) {
            if (length[i] < min) {
                min = length[i];
                pointNumber = i + 1;
            }
        }

        return getNearestPointAddress(pointNumber);
    }
}
