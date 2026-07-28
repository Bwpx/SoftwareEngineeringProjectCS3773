package edu.softwareengineeringprojectcs3773.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.softwareengineeringprojectcs3773.database.DatabaseConnection;
import edu.softwareengineeringprojectcs3773.model.Order;

public class OrderRepository {

    public Order save(Order order) {
        String sql = """
                INSERT INTO orders (
                    account_id,
                    date,
                    total,
                    status,
                    delivery_type,
                    actions
                )
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

    public Order updateOrder(Order order) {
        String sql = """
                UPDATE orders
                SET account_id = ?,
                    date = ?,
                    total = ?,
                    status = ?,
                    delivery_type = ?,
                    actions = ?
                WHERE order_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, order.getAccountId());
            statement.setDate(2, order.getDate());
            statement.setDouble(3, order.getTotal());
            statement.setString(4, order.getStatus());
            statement.setString(5, order.getDeliveryType());
            statement.setString(6, order.getActions());
            statement.setInt(7, order.getOrderId());

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                return order;
            }

        } catch (SQLException e) {
            System.out.println("Error updating order.");
            e.printStackTrace();
        }

        return null;
    }

    public Order findById(int orderId) {
        String sql = """
                SELECT order_id, account_id, date, total, status, delivery_type, actions
                FROM orders
                WHERE order_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createOrderFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding order by ID.");
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Order> findByAccountId(int accountId) {
        String sql = """
                SELECT order_id, account_id, date, total, status, delivery_type, actions
                FROM orders
                WHERE account_id = ?
                """;

        ArrayList<Order> orders = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, accountId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    orders.add(createOrderFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding order by account ID.");
            e.printStackTrace();
        }

        return orders;
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
