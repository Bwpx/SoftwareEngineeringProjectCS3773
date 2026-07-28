package edu.softwareengineeringprojectcs3773.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class TestDatabase {

    public static final String TEST_DATABASE_URL =
            "jdbc:sqlite:data/test.db";

    private static final Path TEST_DATABASE_PATH =
            Paths.get("data", "test.db");

    private TestDatabase() {
        // Utility class
    }

    public static void reset() {
        System.setProperty(
                "roadrunner.database.url",
                TEST_DATABASE_URL
        );

        deleteTestDatabase();
        DatabaseInitializer.initializeDatabase();
        clearTables();
    }

    public static void clearTables() {
        String[] statements = {
                "DELETE FROM addresses",
                "DELETE FROM orders",
                "DELETE FROM grocery_items",
                "DELETE FROM accounts",
                "DELETE FROM sqlite_sequence"
        };

        try (Connection connection =
                     DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            for (String sql : statements) {
                statement.executeUpdate(sql);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Could not clear the test database",
                    e
            );
        }
    }

    public static void deleteTestDatabase() {
        try {
            Files.deleteIfExists(TEST_DATABASE_PATH);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not delete the test database",
                    e
            );
        }
    }

    public static void stopUsingTestDatabase() {
        System.clearProperty("roadrunner.database.url");
    }
}
