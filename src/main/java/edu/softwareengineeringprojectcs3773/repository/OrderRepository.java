package edu.softwareengineeringprojectcs3773.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.softwareengineeringprojectcs3773.database.DatabaseConnection;
import edu.softwareengineeringprojectcs3773.model.Order;

public class OrderRepository {
	
	public Order save(Order order) {
		String sql = """
                INSERT INTO accounts (accountId, date, total, status, delivery_type, actions)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
             )) {

            statement.setInt(1, order.getAccountId());
            statement.setDate(2, order.getDate());
            statement.setDouble(3, order.getTotal());
            statement.setString(4, order.getStatus());
            statement.setString(5, order.getDeliveryType());
            statement.setString(6, order.getActions());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setOrderId(generatedKeys.getInt(1));
                }
            }

            return order;

        } catch (SQLException e) {
            System.out.println("Error saving order.");
            e.printStackTrace();
            return null;
        }
	}
	
	public Order createOrderFromResultSet(ResultSet resultSet) throws SQLException {
		return new Order(
				resultSet.getInt("order_id"),
				resultSet.getInt("account_id"),
				resultSet.getDate("date"),
				resultSet.getDouble("total"),
				resultSet.getString("status"),
				resultSet.getString("delivery_type"),
				resultSet.getString("actions")
		);
	}

}
