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
import java.util.ArrayList;
import java.util.Objects;

public class CustomerWindowManager {

    private static final Connection connection = CarSharing.connection;
    private static final ElementSetter elementSetter = CarSharing.elementSetter;

    // Customer Authorization
    public static void customerAuthorizationWindow() {

        // Create new Stage
        Stage secondStage = new Stage();

        // Create local variables and Object
        AuthorizationManager authorizationManager = new AuthorizationManager(connection);


        // Create Interface Elements
        TextField loginTextField = new TextField();
        Label loginText = new Label("License");
        Button enterButton = new Button("Enter");
        Button registrationButton = new Button("Registration");

        // Interface Elements sizes and settings
        elementSetter.setTextField(	loginTextField,		75,  25, 200, 25);
        elementSetter.setTextLabel(	loginText,			20,  30);
        elementSetter.setButton(	enterButton,		175, 75, 100, 25);
        elementSetter.setButton(	registrationButton,	50,  75, 100, 25);

        // Set on Action Buttons
        enterButton.setOnAction(e -> {
            String login = loginTextField.getText();

            // Check empty value
            if (login.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please, enter login",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {

                // Check Login
                if (authorizationManager.checkCustomerAuthorization(login)) {
                    CustomerManager customerManager = new CustomerManager(connection, login);
                    customerActionWindow(secondStage, customerManager, connection);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Incorrect login, please try again",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        registrationButton.setOnAction(e -> customerRegistrationWindow(secondStage, connection));

        // Create and setting Pane
        Pane root = new Pane();
        root.getChildren().addAll(
                loginTextField,
                loginText,
                enterButton,
                registrationButton);

        // Create and setting window
        Scene scene = new Scene(root, 300, 125);
        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.setTitle("Authorization");
        secondStage.setScene(scene);
        secondStage.show();
    }

    // Customer Action Window
    public static void customerActionWindow(Stage primaryStage, CustomerManager customerManager, Connection connection) {

        // Close Primary Stage
        primaryStage.close();

        // Create new Stage
        Stage secondStage = new Stage();

        // Create local variables and Object
        PointManager pointManager = new PointManager(connection);

        // Create Interface Elements
        Circle customerPoint = pointManager.getPoint();
        Image mapImg = new Image((Objects.requireNonNull(
                CarSharing.class.getClassLoader().getResource("Background.jpg"))).toString());
        ImageView mapImgView = new ImageView(mapImg);
        Button enterButton = new Button("Enter");
        Button exitButton = new Button("Exit");

        // Interface Elements sizes and settings
        elementSetter.setImageView(	mapImgView,		25,  25);
        elementSetter.setButton(	enterButton,	100, 500, 175, 25);
        elementSetter.setButton(	exitButton,		300, 500, 100, 25);

        // Set on Action Buttons
        enterButton.setOnAction(e -> {
            try {
                customerCreateContractWindow(
                        (customerPoint.getCenterX() - 25) + "",
                        (customerPoint.getCenterY() - 25) + "",
                        customerManager,
                        connection,
                        pointManager);
            } catch (IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        "This point has no car today",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        exitButton.setOnAction(e -> {
            customerAuthorizationWindow();
            secondStage.close();
        });

        // Movable point on Image
        pointManager.setPoint(mapImgView, customerPoint);

        // Create and setting Pane
        Pane root = new Pane();
        root.getChildren().addAll(mapImgView, enterButton, exitButton);

        // Create point and add on Image
        root.getChildren().addAll(pointManager.paintCustomerPoint());
        root.getChildren().add(customerPoint);

        // Create and setting window
        Scene scene = new Scene(root, 500, 550);
        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.setTitle(customerManager.getCustomerName());
        secondStage.setScene(scene);
        secondStage.show();
    }

    //Customer Sign Contract
    public static void customerCreateContractWindow(
            String x,
            String y,
            CustomerManager customerManager,
            Connection connection,
            PointManager pointManager) {

        // Create new Stage
        Stage secondStage = new Stage();

        // Create local variables and Object
        CarGetter carGetter = new CarGetter(connection);
        ArrayList<Integer> pointCoordinates = new ArrayList<>(pointManager.getAllPointCoordinateList());
        String nearestPointAddress = pointManager.calculateNearestPoint(x, y, pointCoordinates);

        // Create Interface Elements
        ObservableList<String> listCar = FXCollections.observableArrayList(
                carGetter.getCarOnPointList(nearestPointAddress));
        ComboBox<String> carBox = new ComboBox<>(listCar);
        Label carText = new Label("Choose a car");
        Label pointText = new Label("Nearest point: " + nearestPointAddress);
        Button createContractButton = new Button("Choose this car");
        Button backButton = new Button("Back");

        // Interface Elements sizes and settings
        elementSetter.setComboBox(	carBox,					125, 75,  250, 25);
        elementSetter.setTextLabel(	carText,				25,  80);
        elementSetter.setTextLabel(	pointText,				100, 30);
        elementSetter.setButton(	createContractButton,	125, 125, 150, 25);
        elementSetter.setButton(	backButton,				300, 125, 75,  25);

        // Set on Action Buttons
        createContractButton.setOnAction(e -> {
            try {
                customerManager.createContract(carBox.getValue().substring(7, 13), customerManager.getCustomerID());
                JOptionPane.showMessageDialog(
                        null,
                        "Contract Created!",
                        "Successful",
                        JOptionPane.INFORMATION_MESSAGE);
                secondStage.close();
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please, choose the car",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        backButton.setOnAction(e -> secondStage.close());

        // Create and setting Pane
        Pane root = new Pane();
        root.getChildren().addAll(
                carBox,
                carText,
                pointText,
                createContractButton,
                backButton);

        // Create and setting window
        Scene scene = new Scene(root, 400, 175);
        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.setTitle("Choose a car");
        secondStage.setScene(scene);
        secondStage.show();
    }

    // Customer Registration
    public static void customerRegistrationWindow(Stage primaryStage, Connection connection) {

        // close Primary Stage
        primaryStage.close();

        // Create local variables and Object
        AuthorizationManager authorizationManager = new AuthorizationManager(connection);

        // Create new Stage
        Stage secondStage = new Stage();

        // Create Interface Elements
        TextField firstNameTextField = new TextField();
        TextField lastNameTextField = new TextField();
        TextField licenseTextField = new TextField();
        Label firstNameText = new Label("First name");
        Label lastNameText = new Label("Last name");
        Label licenseText = new Label("Driver's License");
        Button registrationButton = new Button("Registration");
        Button exitButton = new Button("Exit");

        // Interface Elements sizes and settings
        elementSetter.setTextField(	firstNameTextField,	125, 25,  200, 25);
        elementSetter.setTextField(	lastNameTextField,	125, 75,  200, 25);
        elementSetter.setTextField(	licenseTextField,	125, 125, 200, 25);
        elementSetter.setTextLabel(	firstNameText, 		20,  30);
        elementSetter.setTextLabel(	lastNameText, 		20,  80);
        elementSetter.setTextLabel(	licenseText, 		20,  130);
        elementSetter.setButton(	registrationButton,	50,  175, 150, 25);
        elementSetter.setButton(	exitButton,			225, 175, 100, 25);

        // Set on Action Buttons
        registrationButton.setOnAction(e -> {
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String license = licenseTextField.getText();
            if (firstName.equals("") || lastName.equals("") || license.equals("")) {

                JOptionPane.showMessageDialog(
                        null,
                        "Please, fill in all the fields",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if(isNotNumeric(license) || (license.length() > 12)) {

                JOptionPane.showMessageDialog(
                        null,
                        "Please, enter correct value in the license fields",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if((firstName.length() > 10) || (lastName.length() > 10)) {

                JOptionPane.showMessageDialog(
                        null,
                        "Please, enter correct value in the name fields (max length - 10)",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                try {

                    authorizationManager.createCustomer(firstName, lastName, license);
                    JOptionPane.showMessageDialog(
                            null,
                            new String[] {
                                    "Adding Successfully!",
                                    "First name: " + firstName,
                                    "Last name: " + lastName,
                                    "Driver's License: " + license},
                            "Successful",
                            JOptionPane.INFORMATION_MESSAGE);
                    customerAuthorizationWindow();
                    secondStage.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        exitButton.setOnAction(e -> {
            customerAuthorizationWindow();
            secondStage.close();
        });

        // Create and setting Pane
        Pane root = new Pane();
        root.getChildren().addAll(
                firstNameTextField,
                lastNameTextField,
                licenseTextField,
                firstNameText,
                lastNameText,
                licenseText,
                registrationButton,
                exitButton);

        // Create and setting window
        Scene scene = new Scene(root, 350, 225);
        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.setTitle("Customer Registration");
        secondStage.setScene(scene);
        secondStage.show();
    }

    // Other methods
    // Is not numeric
    public static boolean isNotNumeric(String str) {

        try {
            Double.parseDouble(str);
            return false;
        } catch(NumberFormatException e){
            return true;
        }
    }
}
