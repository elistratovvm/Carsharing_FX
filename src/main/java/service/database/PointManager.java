package service.database;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.sql.Connection;
import java.util.ArrayList;

public class PointManager {

    Connection connection;

    public PointManager(Connection connection) {
        this.connection = connection;
    }

    // Create Points
    public Circle[] paintPoint() {

        ReturnCustomerValues rcv = new ReturnCustomerValues(connection);
        ArrayList<Integer> pointCoordinates;
        pointCoordinates = rcv.returnPointCoordinateList();

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

    //Find the nearest Point
    public String nearestPoint(
            String x,
            String y,
            ArrayList<Integer> pointCoordinates) {

        ReturnCustomerValues rcv = new ReturnCustomerValues(connection);

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

        return rcv.getNearestPointAddress(pointNumber);
    }

    // Set location Point
    public void setCustomerPoint(ImageView map, Circle customerPoint) {

        map.setOnMousePressed(me -> {
            customerPoint.setCenterX(me.getX() + 25);
            customerPoint.setCenterY(me.getY() + 25);
        });
    }

    // Get Location Point
    public Circle getCustomerPoint() {

        ReturnCustomerValues rcv = new ReturnCustomerValues(connection);
        ArrayList<Integer> pointCoordinates;
        pointCoordinates = rcv.returnPointCoordinateList();

        int pointQuantity = pointCoordinates.size()/2 ;

        Circle circle = new Circle(
                pointCoordinates.get((pointQuantity - 1)*2) + 25,
                pointCoordinates.get((pointQuantity - 1)*2 + 1) + 25,
                6);
        circle.setStroke(Color.WHITE);

        return circle;
    }
}
