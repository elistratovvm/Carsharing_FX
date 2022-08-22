package application;
	//task completed by Elistratov Vitaliy

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;
	//task completed by Elistratov Vitaliy
public class Carsharing extends Application {
	@Override
	public void start(Stage primaryStage) {				//Start Window
			//Buttons
		Button enterAsCustomerB = new Button("Enter as customer");
		Button enterAsAggregatorB = new Button("Enter as aggregator");
			//Buttons size and settings
		setButton(enterAsCustomerB, 250, 50, 25, 25);
		setButton(enterAsAggregatorB, 250, 50, 25, 100);
			//Set on Action Buttons
		enterAsCustomerB.setOnAction(e -> customerAuthorizationWindow());
		enterAsAggregatorB.setOnAction(e -> aggregatorAuthorizationWindow());
			//Create and setting Pane
		Pane root = new Pane();
		root.getChildren().addAll(enterAsCustomerB, enterAsAggregatorB);
			//Create and setting window
		Scene scene = new Scene(root, 300, 175);
		primaryStage.setTitle("Carsharing");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
		//Windows methods
	public static void customerAuthorizationWindow() {					//Customer Authorization
			//Create new Stage
		Stage secondStage = new Stage();
			//Create local variables and Object
		CustomerAuthorization ca = new CustomerAuthorization();
		ReturnCustomerValues rcv = new ReturnCustomerValues();
			//Create Interface Elements
		TextField loginTF = new TextField();
		Label loginText = new Label("License");
		Button enterB = new Button("Enter");
		Button registrationB = new Button("Registration");
			//Interface Elements sizes and settings
		setTextField(loginTF, 200, 25, 75, 25);
		setTextLabel(loginText, 20, 30);
		setButton(enterB, 100, 25, 175, 75);
		setButton(registrationB, 100, 25, 50, 75);
			//Set on Action Buttons
		enterB.setOnAction(e -> {
			String login = loginTF.getText();
			if (login.equals("")) {					//Check empty value
				JOptionPane.showMessageDialog(null, "Please, enter login", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				if (ca.checkCustomerAuthorization(login)) {		//Check Login
					customerActionWindow(secondStage, rcv.returnCustomerId(login));
				}
				else {
					JOptionPane.showMessageDialog(null, "Incorrect login, please try again",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		registrationB.setOnAction(e -> customerRegistrationWindow(secondStage));
			//Create and setting Pane
		Pane root = new Pane();
		root.getChildren().addAll(loginTF, loginText, enterB, registrationB);
			//Create and setting window
		Scene scene = new Scene(root, 300, 125);
		secondStage.initModality(Modality.APPLICATION_MODAL);
		secondStage.setTitle("Authorization");
		secondStage.setScene(scene);
		secondStage.show();
	}
	public static void customerActionWindow(Stage primaryStage, String login) {	//Customer Action Window
			//close Primary Stage
		primaryStage.close();
			//Create new Stage
		Stage secondStage = new Stage();
		ReturnCustomerValues rcv = new ReturnCustomerValues();
		ArrayList<Integer> pointCoordinates = new ArrayList<Integer>(rcv.returnPointCoordinateList());
			//Create Interface Elements
		Circle customerPoint = getCustomerPoint();
		Image mapImg = new Image(Carsharing.class.getResource("/Background.jpg").toString());
		ImageView mapImgView = new ImageView(mapImg);
		Button enterB = new Button("Enter");
		Button exitB = new Button("Exit");
			//Interface Elements sizes and settings
		mapImgView.setLayoutX(25);
		mapImgView.setLayoutY(25);
		setButton(enterB, 175, 25, 100, 500);
		setButton(exitB, 100, 25, 300, 500);
			//Set on Action Buttons
		enterB.setOnAction(e -> {
			try {
				rcv.returnCarOnPointList(nearestPoint((customerPoint.getCenterX() - 25) + "",
						(customerPoint.getCenterY() - 25) + "", pointCoordinates)).get(0);
				customerCreateContract((customerPoint.getCenterX() - 25) + "",
						(customerPoint.getCenterY() - 25) + "", login);
			}
			catch (IndexOutOfBoundsException ex) {
				JOptionPane.showMessageDialog(null, "This point has no car today", "Error", JOptionPane.ERROR_MESSAGE);
				System.out.println(ex);
			}
			
		});
		exitB.setOnAction(e -> {
			customerAuthorizationWindow();
			secondStage.close();
		});
			//Movable point on Image
		setCustomerPoint(mapImgView, customerPoint);
			//Create and setting Pane
		Pane root = new Pane();
		root.getChildren().addAll(mapImgView, enterB, exitB);
			//Create point and add on Image
		root.getChildren().addAll(paintPoint());
		root.getChildren().add(customerPoint);
			//Create and setting window
		Scene scene = new Scene(root, 500, 550);
		secondStage.initModality(Modality.APPLICATION_MODAL);
		secondStage.setTitle("Customer Window");
		secondStage.setScene(scene);
		secondStage.show();
	}
	public static void customerCreateContract(String x, String y, String login){	//Customer Sign Contract
			//Create new Stage
		Stage secondStage = new Stage();
			//Create local variables and Object
		AddValues av = new AddValues();
		ReturnCustomerValues rcv = new ReturnCustomerValues();
		ArrayList<Integer> pointCoordinates = new ArrayList<Integer>(rcv.returnPointCoordinateList());
		String nearestPointAddress = nearestPoint(x, y, pointCoordinates);
			//Create Interface Elements
		ObservableList<String> listCar = FXCollections.observableArrayList(rcv.returnCarOnPointList(nearestPointAddress));
		ComboBox<String> carBox = new ComboBox<String>(listCar);
		Label carText = new Label("Choose a car");
		Label pointText = new Label("Nearest point: " + nearestPointAddress);
		Button createContractB = new Button("Choose this car");
		Button backB = new Button("Back");
			//Interface Elements sizes and settings
		setComboBox(carBox, 250, 25, 125, 75);
		setTextLabel(carText, 25, 80);
		setTextLabel(pointText, 100, 30);
		setButton(createContractB, 150, 25, 125, 125);
		setButton(backB, 75, 25, 300, 125);
			//Set on Action Buttons
		createContractB.setOnAction(e -> {
			try {
				av.addContract(carBox.getValue().toString().substring(7, 13), login);
				JOptionPane.showMessageDialog(null, "Contract Created!",
						"Successful", JOptionPane.INFORMATION_MESSAGE);
				secondStage.close();
			} catch (NullPointerException e1) {
				JOptionPane.showMessageDialog(null, "Please, choose the car", "Error", JOptionPane.ERROR_MESSAGE);
				System.out.println(e1);
			}
		});
		backB.setOnAction(e -> {
			secondStage.close();
		});
			//Create and setting Pane
		Pane root = new Pane();
		root.getChildren().addAll(carBox, carText, pointText, createContractB, backB);
			//Create and setting window
		Scene scene = new Scene(root, 400, 175);
		secondStage.initModality(Modality.APPLICATION_MODAL);
		secondStage.setTitle("Aggregator Window");
		secondStage.setScene(scene);
		secondStage.show();
	}
	public static void customerRegistrationWindow(Stage primaryStage) {	//Customer Registration
			//close Primary Stage
		primaryStage.close();
			//Create local variables and Object
		AddValues av = new AddValues();
			//Create new Stage
		Stage secondStage = new Stage();
			//Create Interface Elements
		TextField fnameTF = new TextField();
		TextField lnameTF = new TextField();
		TextField licenseTF = new TextField();
		Label fnameText = new Label("First name");
		Label lnameText = new Label("Last name");
		Label licenseText = new Label("Driver's License");
		Button registrationB = new Button("Registration");
		Button exitB = new Button("Exit");
			//Interface Elements sizes and settings
		setTextField(fnameTF, 200, 25, 125, 25);
		setTextField(lnameTF, 200, 25, 125, 75);
		setTextField(licenseTF, 200, 25, 125, 125);
		setTextLabel(fnameText, 20, 30);
		setTextLabel(lnameText, 20, 80);
		setTextLabel(licenseText, 20, 130);
		setButton(registrationB, 150, 25, 50, 175);
		setButton(exitB, 100, 25, 225, 175);
			//Set on Action Buttons
		registrationB.setOnAction(e -> {
			String fname = fnameTF.getText();
			String lname = lnameTF.getText();
			String license = licenseTF.getText();
			if (fname.equals("") || lname.equals("") || license.equals("")) {
				JOptionPane.showMessageDialog(null, "Please, fill in all the fields",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
			else if(isNotNumeric(license) || (license.length() > 12)) {
				JOptionPane.showMessageDialog(null, "Please, enter correct value in the license fields",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
			else if((fname.length() > 10) || (lname.length() > 10)) {
				JOptionPane.showMessageDialog(null, "Please, enter correct value in the name fields (max length - 10)",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				try {
					av.addCustomer(fname, lname, license);
					JOptionPane.showMessageDialog(null, 
						new String[] {"Adding Successfully!",
								"First name: " + fname,
								"Last name: " + lname,
								"Driver's License: " + license
						},
						"Successful", JOptionPane.INFORMATION_MESSAGE);
				customerAuthorizationWindow();
				secondStage.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		});
		exitB.setOnAction(e -> {
			customerAuthorizationWindow();
			secondStage.close();
		});
			//Create and setting Pane
		Pane root = new Pane();
		root.getChildren().addAll(fnameTF, lnameTF, licenseTF, 
				fnameText, lnameText, licenseText, registrationB, exitB);
			//Create and setting window
		Scene scene = new Scene(root, 350, 225);
		secondStage.initModality(Modality.APPLICATION_MODAL);
		secondStage.setTitle("Customer Registration");
		secondStage.setScene(scene);
		secondStage.show();
	}
	public static void aggregatorAuthorizationWindow() {				//Aggregator Authorization
			//Create new Stage
		Stage secondStage = new Stage();
			//Create local variables and Object
		AggregatorAuthorization aa = new AggregatorAuthorization();
			//Create Interface Elements
		TextField loginTF = new TextField();
		Label loginText = new Label("Login");
		Button enterB = new Button("Enter");
			//Interface Elements sizes and settings
		setTextField(loginTF, 200, 25, 75, 25);
		setTextLabel(loginText, 25, 30);
		setButton(enterB, 100, 25, 175, 75);
			//Set on Action Buttons
		enterB.setOnAction(e -> {
			String login = loginTF.getText();
			if (login.equals("")) {
				JOptionPane.showMessageDialog(null, "Please, enter login", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				if (aa.checkAggregatorAuthorization(login)) {
					aggregatorActionWindow(secondStage);
				}
				else {
					JOptionPane.showMessageDialog(null, "Incorrect login, please try again",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
			//Create and setting Pane
		Pane root = new Pane();
		root.getChildren().addAll(loginTF, loginText, enterB);
			//Create and setting window
		Scene scene = new Scene(root, 300, 125);
		secondStage.initModality(Modality.APPLICATION_MODAL);
		secondStage.setTitle("Aggregator Authorization");
		secondStage.setScene(scene);
		secondStage.show();
	}
	public static void aggregatorActionWindow(Stage primaryStage) {		//Aggregator Window
			//close Primary Stage
		primaryStage.close();		
			//Create new Stage
		Stage secondStage = new Stage();
			//Create Interface Elements
		Button newCarB = new Button("Add a new car");
		Button newPointB = new Button("Add a lease point");
			//Interface Elements sizes and settings
		setButton(newCarB, 250, 50, 25, 25);
		setButton(newPointB, 250, 50, 25, 100);
			//Set on Action Buttons
		newCarB.setOnAction(e -> aggregatorAddCar(secondStage));
		newPointB.setOnAction(e -> aggregatorAddPoint(secondStage));
			//Create and setting Pane
		Pane root = new Pane();
		root.getChildren().addAll(newCarB, newPointB);
			//Create and setting window
		Scene scene = new Scene(root, 300, 175);
		secondStage.initModality(Modality.APPLICATION_MODAL);
		secondStage.setTitle("Aggregator Window");
		secondStage.setScene(scene);
		secondStage.show();
	}
	public static void aggregatorAddCar(Stage primaryStage) {			//Aggregator Add Car
			//close Primary Stage
		primaryStage.close();
			//Create new Stage
		Stage secondStage = new Stage();
			//Create local variables and Object
		ReturnAggregatorValues rav = new ReturnAggregatorValues();
		AddValues av = new AddValues();
			//Create Interface Elements
		ObservableList<String> pointList = FXCollections.observableArrayList(rav.returnPointAddressList());
		ObservableList<String> carList = FXCollections.observableArrayList(rav.returnCarList());
		ComboBox<String> pointBox = new ComboBox<String>(pointList);
		ComboBox<String> carBox = new ComboBox<String>(carList);
		TextField carPlateTF = new TextField();
		Label pointText = new Label("Point");
		Label modelText = new Label("Car model");
		Label plateText = new Label("License plate (6)");
		Label rateText = new Label("");
		Button addCarB = new Button("Add");
		Button backB = new Button("Back");
			//Interface Elements sizes and settings
		setComboBox(pointBox, 250, 25, 125, 25);
		setComboBox(carBox, 250, 25, 125, 75);
		setTextField(carPlateTF, 150, 25, 150, 125);
		setTextLabel(pointText, 25, 30);
		setTextLabel(modelText, 25, 80);
		setTextLabel(plateText, 25, 130);
		setButton(addCarB, 175, 25, 75, 175);
		setButton(backB, 100, 25, 275, 175);
			//Set on Action Buttons
		addCarB.setOnAction(e -> {
			String plate = carPlateTF.getText();
			if (plate.equals("") || (plate.length() != 6)) {
				JOptionPane.showMessageDialog(null, "Please, fill correct value in the license plate",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				try {
					av.addCar(pointBox.getValue().toString(), carBox.getValue().toString(), plate);
					JOptionPane.showMessageDialog(null, 
							new String[] {"Adding Successfully!",
									"Point: " + pointBox.getValue().toString(),
									"Car: " + carBox.getValue().toString(),
									"Plate: " + plate
							},
							"Successful", JOptionPane.INFORMATION_MESSAGE);
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(null, "Please, select model and point",
							"Error", JOptionPane.ERROR_MESSAGE);
					System.out.println(ex);
				}
			}
		});
		backB.setOnAction(e ->{
			aggregatorActionWindow(primaryStage);
			secondStage.close();
		});
			//Create and setting Pane
		Pane root = new Pane();
		root.getChildren().addAll(pointBox, carBox, carPlateTF, modelText, 
				pointText, plateText, rateText, addCarB, backB);
			//Create and setting window
		Scene scene = new Scene(root, 400, 225);
		secondStage.initModality(Modality.APPLICATION_MODAL);
		secondStage.setTitle("Add Car");
		secondStage.setScene(scene);
		secondStage.show();		
	}
	public static void aggregatorAddPoint(Stage primaryStage) {			//Aggregator Add Point
			//close Primary Stage
		primaryStage.close();
			//Create local variables and Object
		AddValues av = new AddValues();
			//Create new Stage
		Stage secondStage = new Stage();
			//Create Interface Elements
		TextField addressTF = new TextField();
		Circle customerPoint = getCustomerPoint();
		Image mapImg = new Image(Carsharing.class.getResource("/Background.jpg").toString());
		ImageView mapImgView = new ImageView(mapImg);
		Label addressText = new Label("Adress");
		Button addPointB = new Button("Add a new point");
		Button backB = new Button("Back");
			//Interface Elements sizes and settings
		mapImgView.setLayoutX(25);
		mapImgView.setLayoutY(25);
		setTextField(addressTF, 175, 25, 125, 500);
		setTextLabel(addressText, 25, 505);
		setButton(addPointB, 150, 25, 25, 550);
		setButton(backB, 100, 25, 200, 550);
			//Set on Action Buttons
		addPointB.setOnAction(e ->{
			String address = addressTF.getText();
			String x = customerPoint.getCenterX() + "";
			String y = customerPoint.getCenterY() + "";
			if (address.equals("")) {
				JOptionPane.showMessageDialog(null, "Please, fill the address field",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
			else if (address.length() > 25) {
				JOptionPane.showMessageDialog(null, "Please, enter correct values in the address field (25)",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
			else if ((Double.parseDouble(x) < 0) || (Double.parseDouble(x) > 450) || 
					(Double.parseDouble(y) < 0) || (Double.parseDouble(y) > 450)) {
				JOptionPane.showMessageDialog(null, "Please, enter correct coordinates (0-200)",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				av.addPoint(address, x, y);
				JOptionPane.showMessageDialog(null, new String[] {"Adding Successfully!",
							"Address: " + address,
							"X coordinate: " + x,
							"Y coordinate: " + y
						},
						"Successful", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		backB.setOnAction(e ->{
			aggregatorActionWindow(primaryStage);
			secondStage.close();
		});
			//Movable point on Image
		setCustomerPoint(mapImgView, customerPoint);
			//Create and setting Pane
		Pane root = new Pane();
		root.getChildren().addAll(addressTF, addressText, mapImgView, customerPoint, addPointB, backB);
			//Create and setting window
		Scene scene = new Scene(root, 500, 600);
		secondStage.initModality(Modality.APPLICATION_MODAL);
		secondStage.setTitle("Add Point");
		secondStage.setScene(scene);
		secondStage.show();		
	}
		
		//Interface Elements sizes and settings methods
	public static void setTextField(TextField settableTF, 			//Set Text Field
			int xSize, int ySize, int xLayout, int yLayout) {
		settableTF.setPrefSize(xSize, ySize);
		settableTF.setLayoutX(xLayout);
		settableTF.setLayoutY(yLayout);
	}
	public static void setTextLabel(Label settableLabel, 			//Set Text Label
			int xLayout, int yLayout) {
		settableLabel.setLayoutX(xLayout);
		settableLabel.setLayoutY(yLayout);
	}
	public static void setButton(Button settableB, 					//Set Button
			int xSize, int ySize, int xLayout, int yLayout) {
		settableB.setPrefSize(xSize, ySize);
		settableB.setLayoutX(xLayout);
		settableB.setLayoutY(yLayout);
	}
	public static void setComboBox(ComboBox<String> settableBox,	//Set ComboBox
			int xSize, int ySize, int xLayout, int yLayout) {
		settableBox.setPrefSize(xSize, ySize);
		settableBox.setLayoutX(xLayout);
		settableBox.setLayoutY(yLayout);
	}
	
		//Other methods
	public static boolean isNotNumeric(String str) { 				//Is not numeric
		try {  
			Double.parseDouble(str);  
			return false;
		} catch(NumberFormatException e){  
			return true;  
		}  
	}
	public static Circle[] paintPoint() { 							//Create Points
		ReturnCustomerValues rcv = new ReturnCustomerValues();
		ArrayList<Integer> pointCoordinates = new ArrayList<Integer>();
		pointCoordinates = rcv.returnPointCoordinateList();
		
		int pointQuantity = pointCoordinates.size()/2 ;
		int i;
		
		Circle[] circles = new Circle[pointQuantity + 1];
		for (i = 0; i < pointQuantity; i++) {
			circles[i] = new Circle (pointCoordinates.get(i*2) + 25, pointCoordinates.get(i*2 + 1) + 25, 6);
			circles[i].setStroke(Color.WHITE);
		}
		
		circles[i] = new Circle (pointCoordinates.get((i - 1)*2) + 25, pointCoordinates.get((i - 1)*2 + 1) + 25, 6);
		
		return circles;
	}
	public static String nearestPoint(String x, String y, 			//Find Nearest Point
			ArrayList<Integer> pointCoordinates) { 
		ReturnCustomerValues rcv = new ReturnCustomerValues();
		
		double xInt = Double.parseDouble(x);
		double yInt = Double.parseDouble(y);
		int pointQuantity = pointCoordinates.size()/2;
		double min = 300;
		int pointNumber = -1;
		
		
		double length[] = new double[pointQuantity];
		for (int i = 0; i < pointQuantity; i++) {
			length[i] = Math.sqrt((pointCoordinates.get(i*2) - xInt)*(pointCoordinates.get(i*2) - xInt)
					+ (pointCoordinates.get(i*2 + 1) - yInt)*(pointCoordinates.get(i*2 + 1) - yInt));
		}
		
		for (int i = 0; i < pointQuantity; i++) {
			if (length[i] < min) {
				min = length[i];
				pointNumber = i + 1;
			}
		}
		
		return rcv.getNearesPointAddress(pointNumber);
	}
	public static void setCustomerPoint(ImageView map, Circle customerPoint) {	//Set Location Point
		map.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				customerPoint.setCenterX(me.getX() + 25);
				customerPoint.setCenterY(me.getY() + 25);
			}
		});
	}
	public static Circle getCustomerPoint() { 								//Get Location Point
		ReturnCustomerValues rcv = new ReturnCustomerValues();
		ArrayList<Integer> pointCoordinates = new ArrayList<Integer>();
		pointCoordinates = rcv.returnPointCoordinateList();
		
		int pointQuantity = pointCoordinates.size()/2 ;
		
		Circle circle = new Circle (pointCoordinates.get((pointQuantity - 1)*2)
				+ 25, pointCoordinates.get((pointQuantity - 1)*2 + 1) + 25, 6);
		circle.setStroke(Color.WHITE);
		
		return circle;
	}
	
	//Main method
	public static void main(String[] args) {
		launch(args);
	}
}