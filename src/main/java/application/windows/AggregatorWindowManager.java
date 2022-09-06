package application.windows;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.ElementSetter;
import service.database.*;

import javax.swing.*;
import java.sql.Connection;
import java.util.Objects;

public class AggregatorWindowManager {

    private static final Connection connection = CarSharing.connection;
    private static final ElementSetter elementSetter = CarSharing.elementSetter;

    // Aggregator Authorization
    public static void aggregatorAuthorizationWindow() {

        // Create new Stage
        Stage secondStage = new Stage();

        // Create local variables and Object
        AuthorizationManager authorizationManager = new AuthorizationManager(connection);

        // Create Interface Elements
        TextField loginTextField = new TextField();
        Label loginText = new Label("Login");
        Button enterButton = new Button("Enter");

        // Interface Elements sizes and settings
        elementSetter.setTextField(	loginTextField,	75,  25, 200, 25);
        elementSetter.setTextLabel(	loginText, 		25,  30);
        elementSetter.setButton(	enterButton,	175, 75, 100, 25);

        // Set on Action Buttons
        enterButton.setOnAction(e -> {
            String login = loginTextField.getText();
            if (login.equals("")) {

                JOptionPane.showMessageDialog(
                        null,
                        "Please, enter login",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                if (authorizationManager.checkAggregatorAuthorization(login)) {

                    aggregatorActionWindow(secondStage, connection, login);
                } else {

                    JOptionPane.showMessageDialog(
                            null,
                            "Incorrect login, please try again",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create and setting Pane
        Pane root = new Pane();
        root.getChildren().addAll(loginTextField, loginText, enterButton);

        // Create and setting window
        Scene scene = new Scene(root, 300, 125);
        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.setTitle("Aggregator Authorization");
        secondStage.setScene(scene);
        secondStage.show();
    }

    // Aggregator Window
    public static void aggregatorActionWindow(Stage primaryStage, Connection connection, String login) {

        // Close Primary Stage
        primaryStage.close();

        // Create new Stage
        Stage secondStage = new Stage();

        // Create local variables and Object
        AggregatorManager aggregatorManager = new AggregatorManager(connection, login);
        PointManager pointManager = new PointManager(connection);

        // Create Interface Elements
        Button newCarButton = new Button("Add a new car");
        Button newPointButton = new Button("Add a lease point");

        // Interface Elements sizes and settings
        elementSetter.setButton(newCarButton,	25, 25,  250, 50);
        elementSetter.setButton(newPointButton,	25, 100, 250, 50);

        // Set on Action Buttons
        newCarButton.setOnAction(e -> aggregatorAddCarWindow(connection, aggregatorManager, pointManager));
        newPointButton.setOnAction(e -> aggregatorAddPointWindow(aggregatorManager, pointManager));

        // Create and setting Pane
        Pane root = new Pane();
        root.getChildren().addAll(newCarButton, newPointButton);

        // Create and setting window
        Scene scene = new Scene(root, 300, 175);
        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.setTitle("Aggregator Window");
        secondStage.setScene(scene);
        secondStage.show();
    }

    // Aggregator Add Car
    public static void aggregatorAddCarWindow(
            Connection connection,
            AggregatorManager aggregatorManager,
            PointManager pointManager) {

        // Create new Stage
        Stage secondStage = new Stage();

        // Create local variables and Object
        CarGetter carGetter = new CarGetter(connection);

        // Create Interface Elements
        ObservableList<String> pointList = FXCollections.observableArrayList(pointManager.getPointAddressList());
        ObservableList<String> carList = FXCollections.observableArrayList(carGetter.getCarList());
        ComboBox<String> pointBox = new ComboBox<>(pointList);
        ComboBox<String> carBox = new ComboBox<>(carList);
        TextField carPlateTextField = new TextField();
        Label pointText = new Label("Point");
        Label modelText = new Label("Car model");
        Label plateText = new Label("License plate (6)");
        Label rateText = new Label("");
        Button addCarButton = new Button("Add");
        Button backButton = new Button("Back");

        // Interface Elements sizes and settings
        elementSetter.setComboBox(	pointBox,			125, 25,  250, 25);
        elementSetter.setComboBox(	carBox,				125, 75,  250, 25);
        elementSetter.setTextField(	carPlateTextField,	150, 125, 150, 25);
        elementSetter.setTextLabel(	pointText, 			25,  30);
        elementSetter.setTextLabel(	modelText, 			25,  80);
        elementSetter.setTextLabel(	plateText, 			25,  130);
        elementSetter.setButton(	addCarButton,		75,  175, 175, 25);
        elementSetter.setButton(	backButton,			275, 175, 100, 25);

        // Set on Action Buttons
        addCarButton.setOnAction(e -> {
            String plate = carPlateTextField.getText();
            if ("".equals(plate) || (plate.length() != 6)) {

                JOptionPane.showMessageDialog(
                        null,
                        "Please, fill correct value in the license plate",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                try {

                    aggregatorManager.addCar(pointBox.getValue(), carBox.getValue(), plate);
                    JOptionPane.showMessageDialog(
                            null,
                            new String[] {
                                    "Adding Successfully!",
                                    "Point: " + pointBox.getValue(),
                                    "Car: " + carBox.getValue(),
                                    "Plate: " + plate},
                            "Successful",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Please, select model and point",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        backButton.setOnAction(e -> secondStage.close());

        // Create and setting Pane
        Pane root = new Pane();
        root.getChildren().addAll(
                pointBox,
                carBox,
                carPlateTextField,
                modelText,
                pointText,
                plateText,
                rateText,
                addCarButton,
                backButton);

        // Create and setting window
        Scene scene = new Scene(root, 400, 225);
        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.setTitle("Add Car");
        secondStage.setScene(scene);
        secondStage.show();
    }

    //Aggregator Add Point
    public static void aggregatorAddPointWindow(AggregatorManager aggregatorManager, PointManager pointManager) {

        // Create new Stage
        Stage secondStage = new Stage();

        // Create Interface Elements
        TextField addressTextField = new TextField();
        Circle newPoint = pointManager.getPoint();
        Image mapImg = new Image((Objects.requireNonNull(
                CarSharing.class.getClassLoader().getResource("Background.jpg"))).toString());
        ImageView mapImgView = new ImageView(mapImg);
        Label addressText = new Label("Address");
        Button addPointButton = new Button("Add a new point");
        Button backButton = new Button("Back");

        // Interface Elements sizes and settings
        elementSetter.setImageView(	mapImgView, 		25,  25);
        elementSetter.setTextField(	addressTextField,	125, 500, 175, 25);
        elementSetter.setTextLabel(	addressText, 		25,  505);
        elementSetter.setButton(	addPointButton,		25,  550, 150, 25);
        elementSetter.setButton(	backButton, 		200, 550, 100, 25);

        // Set on Action Buttons
        addPointButton.setOnAction(e ->{
            String address = addressTextField.getText();
            String x = newPoint.getCenterX() - 25 + "";
            String y = newPoint.getCenterY() - 25 + "";
            if (address.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please, fill the address field",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if (address.length() > 25) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please, enter correct values in the address field (25)",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if ((Double.parseDouble(x) < 0)
                    ||(Double.parseDouble(x) > 450)
                    || (Double.parseDouble(y) < 0)
                    || (Double.parseDouble(y) > 450)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please, enter correct coordinates (0-200)",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else {
                aggregatorManager.addPoint(address, x, y);
                JOptionPane.showMessageDialog(
                        null,
                        new String[] {
                                "Adding Successfully!",
                                "Address: " + address,
                                "X coordinate: " + x,
                                "Y coordinate: " + y},
                        "Successful",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        backButton.setOnAction(e ->	secondStage.close());

        // Movable point on Image
        pointManager.setPoint(mapImgView, newPoint);

        // Create and setting Pane
        Pane root = new Pane();
        root.getChildren().addAll(
                addressTextField,
                addressText,
                mapImgView,
                newPoint,
                addPointButton,
                backButton);
        root.getChildren().addAll(pointManager.paintAggregatorPoint(aggregatorManager.getAggregatorID()));

        // Create and setting window
        Scene scene = new Scene(root, 500, 600);
        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.setTitle("Add Point");
        secondStage.setScene(scene);
        secondStage.show();
    }
}
