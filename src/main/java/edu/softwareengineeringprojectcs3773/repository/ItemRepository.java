package edu.softwareengineeringprojectcs3773.repository;

import edu.softwareengineeringprojectcs3773.database.DatabaseConnection;
import edu.softwareengineeringprojectcs3773.model.GroceryItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ItemRepository {

    public GroceryItem save(GroceryItem item) {
        String sql = """
                INSERT INTO grocery_items (
                    item_name,
                    category,
                    price,
                    quantity_in_stock
                )
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
             )) {

            statement.setString(1, item.getItemName());
            statement.setString(2, item.getCategory());
            statement.setDouble(3, item.getPrice());
            statement.setInt(4, item.getQuantityInStock());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setItemId(generatedKeys.getInt(1));
                }
            }

            return item;

        } catch (SQLException e) {
            System.out.println("Error saving grocery item.");
            e.printStackTrace();
            return null;
        }
    }

    public GroceryItem findById(int itemId) {
        String sql = """
                SELECT item_id, item_name, category, price, quantity_in_stock
                FROM grocery_items
                WHERE item_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, itemId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createItemFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding grocery item by ID.");
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<GroceryItem> findAll() {
        ArrayList<GroceryItem> items = new ArrayList<>();

        String sql = """
                SELECT item_id, item_name, category, price, quantity_in_stock
                FROM grocery_items
                ORDER BY item_id
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                items.add(createItemFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving grocery items.");
            e.printStackTrace();
        }

        return items;
    }

    public ArrayList<GroceryItem> searchByName(String searchTerm) {
        ArrayList<GroceryItem> results = new ArrayList<>();

        String sql = """
                SELECT item_id, item_name, category, price, quantity_in_stock
                FROM grocery_items
                WHERE LOWER(item_name) LIKE LOWER(?)
                ORDER BY item_name
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + searchTerm + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(createItemFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error searching grocery items by name.");
            e.printStackTrace();
        }

        return results;
    }

    public ArrayList<GroceryItem> searchByCategory(String category) {
        ArrayList<GroceryItem> results = new ArrayList<>();

        String sql = """
                SELECT item_id, item_name, category, price, quantity_in_stock
                FROM grocery_items
                WHERE LOWER(category) = LOWER(?)
                ORDER BY item_name
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, category);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(createItemFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error searching grocery items by category.");
            e.printStackTrace();
        }

        return results;
    }

    public ArrayList<GroceryItem> findInStockItems() {
        ArrayList<GroceryItem> results = new ArrayList<>();

        String sql = """
                SELECT item_id, item_name, category, price, quantity_in_stock
                FROM grocery_items
                WHERE quantity_in_stock > 0
                ORDER BY item_name
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                results.add(createItemFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving in-stock grocery items.");
            e.printStackTrace();
        }

        return results;
    }

    public boolean updateStock(int itemId, int newQuantity) {
        String sql = """
                UPDATE grocery_items
                SET quantity_in_stock = ?
                WHERE item_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, newQuantity);
            statement.setInt(2, itemId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating grocery item stock.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean isEmpty() {
        String sql = "SELECT COUNT(*) FROM grocery_items";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1) == 0;
            }

        } catch (SQLException e) {
            System.out.println("Error checking grocery items table.");
            e.printStackTrace();
        }

        return true;
    }

    public void loadSampleItems() {
        if (!isEmpty()) {
            return;
        }

        save(new GroceryItem(0, "Milk", "Dairy", 3.49, 20));
        save(new GroceryItem(0, "Eggs", "Dairy", 4.29, 15));
        save(new GroceryItem(0, "Bread", "Bakery", 2.99, 30));
        save(new GroceryItem(0, "Apples", "Produce", 1.49, 50));
        save(new GroceryItem(0, "Chicken Breast", "Meat", 8.99, 12));
        save(new GroceryItem(0, "Rice", "Pantry", 5.99, 25));

        System.out.println("Sample grocery items added.");
    }

    private GroceryItem createItemFromResultSet(ResultSet resultSet)
            throws SQLException {

        return new GroceryItem(
                resultSet.getInt("item_id"),
                resultSet.getString("item_name"),
                resultSet.getString("category"),
                resultSet.getDouble("price"),
                resultSet.getInt("quantity_in_stock")
        );
    }
}
