package edu.softwareengineeringprojectcs3773.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseInitializerTest {

    @BeforeEach
    void setUp() {
        TestDatabase.reset();
    }

    @AfterAll
    static void tearDownAll() {
        TestDatabase.deleteTestDatabase();
        TestDatabase.stopUsingTestDatabase();
    }

    @Test
    void initializeDatabaseCreatesRequiredTablesAndColumns()
            throws SQLException {

        assertTrue(tableExists("accounts"));
        assertTrue(tableExists("grocery_items"));
        assertTrue(tableExists("orders"));
        assertTrue(tableExists("addresses"));

        assertTrue(columnExists(
                "accounts",
                "first_name"
        ));

        assertTrue(columnExists(
                "accounts",
                "last_name"
        ));

        assertTrue(columnExists(
                "orders",
                "total"
        ));

        assertEquals(
                "REAL",
                getColumnType(
                        "orders",
                        "total"
                )
        );
    }

    private boolean tableExists(String tableName)
            throws SQLException {

        String sql = """
                SELECT name
                FROM sqlite_master
                WHERE type = 'table'
                  AND name = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, tableName);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                return resultSet.next();
            }
        }
    }

    private boolean columnExists(
            String tableName,
            String columnName
    ) throws SQLException {

        return getColumnType(
                tableName,
                columnName
        ) != null;
    }

    private String getColumnType(
            String tableName,
            String columnName
    ) throws SQLException {

        String sql =
                "PRAGMA table_info(" + tableName + ")";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {
                if (columnName.equalsIgnoreCase(
                        resultSet.getString("name")
                )) {
                    return resultSet.getString("type");
                }
            }
        }

        return null;
    }
}
