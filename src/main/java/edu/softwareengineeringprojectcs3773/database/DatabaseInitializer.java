package edu.softwareengineeringprojectcs3773.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        createAccountsTable();
        createGroceryItemsTable();
    }

    private static void createAccountsTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS accounts (
                    account_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL UNIQUE,
                    email TEXT NOT NULL UNIQUE,
                    password TEXT NOT NULL,
                    phone_number TEXT
                );
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);
            System.out.println("Accounts table ready.");

        } catch (SQLException e) {
            System.out.println("Error creating accounts table.");
            e.printStackTrace();
        }
    }

    private static void createGroceryItemsTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS grocery_items (
                    item_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    item_name TEXT NOT NULL,
                    category TEXT NOT NULL,
                    price REAL NOT NULL,
                    quantity_in_stock INTEGER NOT NULL
                );
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);
            System.out.println("Grocery items table ready.");

        } catch (SQLException e) {
            System.out.println("Error creating grocery items table.");
            e.printStackTrace();
        }
    }
}
