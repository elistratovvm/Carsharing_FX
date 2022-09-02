package service.database;

import java.sql.Connection;
import java.sql.Statement;

public class AggregatorManager {

	Connection connection;
	Servicer servicer;

	private String aggregatorID;

	public AggregatorManager(Connection connection, String aggregatorID) {

		this.connection = connection;
		this.servicer = new Servicer(connection);
		this.setAggregatorID(aggregatorID);
	}

	// Add Car
	public void addCar(String point, String car, String plate) {

		try {
			String lastIdCar = servicer.getLastValueId("SELECT id FROM public.cars");
			String query1 = "INSERT INTO public.cars (" +
					"id, license_plate, technical_condition, fuel, cte_brand_and_model, agr_id) " +
					"VALUES (" + lastIdCar + ", '" + plate + "', 'Отличное', 100, '" + car + "', " + aggregatorID + ");";
			String query2 = "INSERT INTO public.car_lease_point_details (car_id, lpt_id)" +
					"VALUES (" + lastIdCar + ", '" + new PointManager(connection).getPointIDFromAddress(point) + "')";

			Statement statement1 = connection.createStatement();
			Statement statement2 = connection.createStatement();

			statement1.execute(query1);
			statement2.execute(query2);

			statement1.close();
			statement2.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Add Point
	public void addPoint(String address, String x, String y) {

		try {
			String lastIdPoint = servicer.getLastValueId("SELECT id FROM public.lease_points");
			String query = "INSERT INTO public.lease_points (id, address, x_coordinate, y_coordinate, agr_id) " +
					"VALUES (" + lastIdPoint + ", '" + address + "', " + x + ", " + y + ", " + aggregatorID + ")";

			Statement statement = connection.createStatement();

			statement.execute(query);

			statement.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setAggregatorID(String aggregatorID) {
		this.aggregatorID = aggregatorID;
	}
}
