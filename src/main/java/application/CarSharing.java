package application;

import javafx.application.Application;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

public class CarSharing extends Application {

	@Override
	// Start Window
	public void start(Stage primaryStage) {

		// Buttons
		Button enterAsCustomerButton = new Button("Enter as customer");
		Button enterAsAggregatorButton = new Button("Enter as aggregator");

		// Buttons size and settings
		setButton(enterAsCustomerButton, 250, 50, 25, 25);
		setButton(enterAsAggregatorButton, 250, 50, 25, 100);

		// Set on Action Buttons
		enterAsCustomerButton.setOnAction(e -> customerAuthorizationWindow());
		enterAsAggregatorButton.setOnAction(e -> aggregatorAuthorizationWindow());

		// Create and setting Pane
		Pane root = new Pane();
		root.getChildren().addAll(enterAsCustomerButton, enterAsAggregatorButton);

		// Create and setting window
		Scene scene = new Scene(root, 300, 175);
		primaryStage.setTitle("Car sharing App");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// Windows methods
	// Customer Authorization
	public static void customerAuthorizationWindow() {

		// Create new Stage
		Stage secondStage = new Stage();

		// Create local variables and Object
		CustomerAuthorization customerAuthorization = new CustomerAuthorization();
		ReturnCustomerValues returnCustomerValues = new ReturnCustomerValues();

		// Create Interface Elements
		TextField loginTextField = new TextField();
		Label loginText = new Label("License");
		Button enterButton = new Button("Enter");
		Button registrationButton = new Button("Registration");

		// Interface Elements sizes and settings
		setTextField(loginTextField, 200, 25, 75, 25);
		setTextLabel(loginText, 20, 30);
		setButton(enterButton, 100, 25, 175, 75);
		setButton(registrationButton, 100, 25, 50, 75);

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
			}
			else {

				// Check Login
				if (customerAuthorization.checkCustomerAuthorization(login)) {
					customerActionWindow(secondStage, returnCustomerValues.returnCustomerId(login));
				}
				else {
					JOptionPane.showMessageDialog(
							null,
							"Incorrect login, please try again",
							"Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		registrationButton.setOnAction(e -> customerRegistrationWindow(secondStage));

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
	public static void customerActionWindow(Stage primaryStage, String login) {

		// Close Primary Stage
		primaryStage.close();

		// Create new Stage
		Stage secondStage = new Stage();
		ReturnCustomerValues returnCustomerValues = new ReturnCustomerValues();
		ArrayList<Integer> pointCoordinates = new ArrayList<>(returnCustomerValues.returnPointCoordinateList());

		// Create Interface Elements
		Circle customerPoint = getCustomerPoint();
		Image mapImg = new Image(Objects.requireNonNull(CarSharing.class.getResource("/Background.jpg")).toString());
		ImageView mapImgView = new ImageView(mapImg);
		Button enterButton = new Button("Enter");
		Button exitButton = new Button("Exit");

		// Interface Elements sizes and settings
		mapImgView.setLayoutX(25);
		mapImgView.setLayoutY(25);
		setButton(enterButton, 175, 25, 100, 500);
		setButton(exitButton, 100, 25, 300, 500);

		// Set on Action Buttons
		enterButton.setOnAction(e -> {
			try {
				returnCustomerValues.returnCarOnPointList(nearestPoint(
						(customerPoint.getCenterX() - 25) + "",
						(customerPoint.getCenterY() - 25) + "", pointCoordinates)
						).get(0);
				customerCreateContract(
						(customerPoint.getCenterX() - 25) + "",
						(customerPoint.getCenterY() - 25) + "",
						login);
			}
			catch (IndexOutOfBoundsException ex) {
				JOptionPane.showMessageDialog(
						null,
						"This point has no car today",
						"Error",
						JOptionPane.ERROR_MESSAGE);
				System.out.println(ex);
			}
			
		});
		exitButton.setOnAction(e -> {
			customerAuthorizationWindow();
			secondStage.close();
		});

		// Movable point on Image
		setCustomerPoint(mapImgView, customerPoint);

		// Create and setting Pane
		Pane root = new Pane();
		root.getChildren().addAll(mapImgView, enterButton, exitButton);

		// Create point and add on Image
		root.getChildren().addAll(paintPoint());
		root.getChildren().add(customerPoint);

		// Create and setting window
		Scene scene = new Scene(root, 500, 550);
		secondStage.initModality(Modality.APPLICATION_MODAL);
		secondStage.setTitle("Customer Window");
		secondStage.setScene(scene);
		secondStage.show();
	}

	//Customer Sign Contract
	public static void customerCreateContract(String x, String y, String login) {

		// Create new Stage
		Stage secondStage = new Stage();

		// Create local variables and Object
		AddValues addValues = new AddValues();
		ReturnCustomerValues returnCustomerValues = new ReturnCustomerValues();
		ArrayList<Integer> pointCoordinates = new ArrayList<>(returnCustomerValues.returnPointCoordinateList());
		String nearestPointAddress = nearestPoint(x, y, pointCoordinates);

		// Create Interface Elements
		ObservableList<String> listCar = FXCollections.observableArrayList(returnCustomerValues.returnCarOnPointList(nearestPointAddress));
		ComboBox<String> carBox = new ComboBox<>(listCar);
		Label carText = new Label("Choose a car");
		Label pointText = new Label("Nearest point: " + nearestPointAddress);
		Button createContractButton = new Button("Choose this car");
		Button backButton = new Button("Back");

		// Interface Elements sizes and settings
		setComboBox(carBox, 250, 25, 125, 75);
		setTextLabel(carText, 25, 80);
		setTextLabel(pointText, 100, 30);
		setButton(createContractButton, 150, 25, 125, 125);
		setButton(backButton, 75, 25, 300, 125);

		// Set on Action Buttons
		createContractButton.setOnAction(e -> {
			try {
				addValues.addContract(carBox.getValue().substring(7, 13), login);
				JOptionPane.showMessageDialog(
						null,
						"Contract Created!",
						"Successful",
						JOptionPane.INFORMATION_MESSAGE);
				secondStage.close();
			} catch (NullPointerException e1) {
				JOptionPane.showMessageDialog(
						null,
						"Please, choose the car",
						"Error",
						JOptionPane.ERROR_MESSAGE);
				System.out.println(e1);
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
		secondStage.setTitle("Aggregator Window");
		secondStage.setScene(scene);
		secondStage.show();
	}

	// Customer Registration
	public static void customerRegistrationWindow(Stage primaryStage) {

		// close Primary Stage
		primaryStage.close();

		// Create local variables and Object
		AddValues addValues = new AddValues();

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
		setTextField(firstNameTextField, 200, 25, 125, 25);
		setTextField(lastNameTextField, 200, 25, 125, 75);
		setTextField(licenseTextField, 200, 25, 125, 125);
		setTextLabel(firstNameText, 20, 30);
		setTextLabel(lastNameText, 20, 80);
		setTextLabel(licenseText, 20, 130);
		setButton(registrationButton, 150, 25, 50, 175);
		setButton(exitButton, 100, 25, 225, 175);

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
			}
			else if(isNotNumeric(license) || (license.length() > 12)) {
				JOptionPane.showMessageDialog(
						null,
						"Please, enter correct value in the license fields",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			}
			else if((firstName.length() > 10) || (lastName.length() > 10)) {
				JOptionPane.showMessageDialog(
						null,
						"Please, enter correct value in the name fields (max length - 10)",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			}
			else {
				try {
					addValues.addCustomer(firstName, lastName, license);
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
					System.out.println(ex);
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

	// Aggregator Authorization
	public static void aggregatorAuthorizationWindow() {

		// Create new Stage
		Stage secondStage = new Stage();

		// Create local variables and Object
		AggregatorAuthorization aggregatorAuthorization = new AggregatorAuthorization();

		// Create Interface Elements
		TextField loginTextField = new TextField();
		Label loginText = new Label("Login");
		Button enterButton = new Button("Enter");

		// Interface Elements sizes and settings
		setTextField(loginTextField, 200, 25, 75, 25);
		setTextLabel(loginText, 25, 30);
		setButton(enterButton, 100, 25, 175, 75);

		// Set on Action Buttons
		enterButton.setOnAction(e -> {
			String login = loginTextField.getText();
			if (login.equals("")) {
				JOptionPane.showMessageDialog(
						null,
						"Please, enter login",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			}
			else {
				if (aggregatorAuthorization.checkAggregatorAuthorization(login)) {
					aggregatorActionWindow(secondStage);
				}
				else {
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
	public static void aggregatorActionWindow(Stage primaryStage) {

		// Close Primary Stage
		primaryStage.close();

		// Create new Stage
		Stage secondStage = new Stage();

		// Create Interface Elements
		Button newCarButton = new Button("Add a new car");
		Button newPointButton = new Button("Add a lease point");

		// Interface Elements sizes and settings
		setButton(newCarButton, 250, 50, 25, 25);
		setButton(newPointButton, 250, 50, 25, 100);

		// Set on Action Buttons
		newCarButton.setOnAction(e -> aggregatorAddCar(secondStage));
		newPointButton.setOnAction(e -> aggregatorAddPoint(secondStage));

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
	public static void aggregatorAddCar(Stage primaryStage) {

		// Close Primary Stage
		primaryStage.close();

		// Create new Stage
		Stage secondStage = new Stage();

		// Create local variables and Object
		ReturnAggregatorValues returnAggregatorValues = new ReturnAggregatorValues();
		AddValues addValues = new AddValues();

		// Create Interface Elements
		ObservableList<String> pointList = FXCollections.observableArrayList(returnAggregatorValues.returnPointAddressList());
		ObservableList<String> carList = FXCollections.observableArrayList(returnAggregatorValues.returnCarList());
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
		setComboBox(pointBox, 250, 25, 125, 25);
		setComboBox(carBox, 250, 25, 125, 75);
		setTextField(carPlateTextField, 150, 25, 150, 125);
		setTextLabel(pointText, 25, 30);
		setTextLabel(modelText, 25, 80);
		setTextLabel(plateText, 25, 130);
		setButton(addCarButton, 175, 25, 75, 175);
		setButton(backButton, 100, 25, 275, 175);

		// Set on Action Buttons
		addCarButton.setOnAction(e -> {
			String plate = carPlateTextField.getText();
			if ("".equals(plate) || (plate.length() != 6)) {
				JOptionPane.showMessageDialog(
						null,
						"Please, fill correct value in the license plate",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			}
			else {
				try {
					addValues.addCar(pointBox.getValue(), carBox.getValue(), plate);
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
					System.out.println(ex);
				}
			}
		});
		backButton.setOnAction(e ->{
			aggregatorActionWindow(primaryStage);
			secondStage.close();
		});

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
	public static void aggregatorAddPoint(Stage primaryStage) {

		// close Primary Stage
		primaryStage.close();

		// Create local variables and Object
		AddValues addValues = new AddValues();

		// Create new Stage
		Stage secondStage = new Stage();

		// Create Interface Elements
		TextField addressTextField = new TextField();
		Circle customerPoint = getCustomerPoint();
		Image mapImg = new Image(Objects.requireNonNull(CarSharing.class.getResource("/Background.jpg")).toString());
		ImageView mapImgView = new ImageView(mapImg);
		Label addressText = new Label("Address");
		Button addPointButton = new Button("Add a new point");
		Button backButton = new Button("Back");

		// Interface Elements sizes and settings
		mapImgView.setLayoutX(25);
		mapImgView.setLayoutY(25);
		setTextField(addressTextField, 175, 25, 125, 500);
		setTextLabel(addressText, 25, 505);
		setButton(addPointButton, 150, 25, 25, 550);
		setButton(backButton, 100, 25, 200, 550);

		// Set on Action Buttons
		addPointButton.setOnAction(e ->{
			String address = addressTextField.getText();
			String x = customerPoint.getCenterX() + "";
			String y = customerPoint.getCenterY() + "";
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
				addValues.addPoint(address, x, y);
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
		backButton.setOnAction(e ->{
			aggregatorActionWindow(primaryStage);
			secondStage.close();
		});

		// Movable point on Image
		setCustomerPoint(mapImgView, customerPoint);

		// Create and setting Pane
		Pane root = new Pane();
		root.getChildren().addAll(
				addressTextField,
				addressText,
				mapImgView,
				customerPoint,
				addPointButton,
				backButton);

		// Create and setting window
		Scene scene = new Scene(root, 500, 600);
		secondStage.initModality(Modality.APPLICATION_MODAL);
		secondStage.setTitle("Add Point");
		secondStage.setScene(scene);
		secondStage.show();		
	}
		
	// Interface Elements sizes and settings methods
	// Set Text Field
	public static void setTextField(
			TextField settableTF,
			int xSize,
			int ySize,
			int xLayout,
			int yLayout) {

		settableTF.setPrefSize(xSize, ySize);
		settableTF.setLayoutX(xLayout);
		settableTF.setLayoutY(yLayout);
	}

	// Set Text Label
	public static void setTextLabel(
			Label settableLabel,
			int xLayout,
			int yLayout) {

		settableLabel.setLayoutX(xLayout);
		settableLabel.setLayoutY(yLayout);
	}

	// Set Button
	public static void setButton(
			Button settableB,
			int xSize,
			int ySize,
			int xLayout,
			int yLayout) {

		settableB.setPrefSize(xSize, ySize);
		settableB.setLayoutX(xLayout);
		settableB.setLayoutY(yLayout);
	}

	// Set ComboBox
	public static void setComboBox(
			ComboBox<String> settableBox,
			int xSize,
			int ySize,
			int xLayout,
			int yLayout) {

		settableBox.setPrefSize(xSize, ySize);
		settableBox.setLayoutX(xLayout);
		settableBox.setLayoutY(yLayout);
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

	// Create Points
	public static Circle[] paintPoint() {

		ReturnCustomerValues rcv = new ReturnCustomerValues();
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
	public static String nearestPoint(
			String x,
			String y,
			ArrayList<Integer> pointCoordinates) {

		ReturnCustomerValues rcv = new ReturnCustomerValues();
		
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
	public static void setCustomerPoint(ImageView map, Circle customerPoint) {

		map.setOnMousePressed(me -> {
			customerPoint.setCenterX(me.getX() + 25);
			customerPoint.setCenterY(me.getY() + 25);
		});
	}

	// Get Location Point
	public static Circle getCustomerPoint() {

		ReturnCustomerValues rcv = new ReturnCustomerValues();
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
	
	// Main method
	public static void main(String[] args) {
		launch(args);
	}
}