package application.windows;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.ElementSetter;
import service.database.*;

import java.sql.Connection;
import java.sql.SQLException;

public class CarSharing extends Application {

	static Connection connection;
	static ElementSetter elementSetter = new ElementSetter();

	@Override
	// Start Window
	public void start(Stage primaryStage) {

		// Buttons
		Button enterAsCustomerButton = new Button("Enter as customer");
		Button enterAsAggregatorButton = new Button("Enter as aggregator");

		// Buttons size and settings
		elementSetter.setButton(enterAsCustomerButton,		25, 25,  250, 50);
		elementSetter.setButton(enterAsAggregatorButton,	25, 100, 250, 50);

		// Set on Action Buttons
		enterAsCustomerButton.setOnAction(e -> CustomerWindowManager.customerAuthorizationWindow());
		enterAsAggregatorButton.setOnAction(e -> AggregatorWindowManager.aggregatorAuthorizationWindow());

		// Create and setting Pane
		Pane root = new Pane();
		root.getChildren().addAll(enterAsCustomerButton, enterAsAggregatorButton);

		// Create and setting window
		Scene scene = new Scene(root, 300, 175);
		primaryStage.setTitle("Car sharing App");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(e -> {
			try {
				connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		});
	}

	// Main method
	public static void main(String[] args) {

		try {
			connection = new DatabaseConnector(args[0], args[1], args[2]).getConnection();
		} catch (ArrayIndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		launch(args);
	}
}