package edu.softwareengineeringprojectcs3773.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        createAccountsTable();
        migrateAccountNameColumns();
        createGroceryItemsTable();
        createOrdersTable();
        migrateOrdersTotalToReal();
    }

    private static void createAccountsTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS accounts (
                    account_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    first_name TEXT,
                    last_name TEXT,
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

    private static void migrateAccountNameColumns() {
        try (Connection connection = DatabaseConnection.getConnection()) {

            boolean hasFirstName = columnExists(
                    connection,
                    "accounts",
                    "first_name"
            );

            boolean hasLastName = columnExists(
                    connection,
                    "accounts",
                    "last_name"
            );

            try (Statement statement = connection.createStatement()) {
                if (!hasFirstName) {
                    statement.execute(
                            "ALTER TABLE accounts ADD COLUMN first_name TEXT"
                    );
                }

                if (!hasLastName) {
                    statement.execute(
                            "ALTER TABLE accounts ADD COLUMN last_name TEXT"
                    );
                }
            }

            System.out.println("Account name columns ready.");

        } catch (SQLException e) {
            System.out.println("Error migrating account name columns.");
            e.printStackTrace();
        }
    }

    private static boolean columnExists(
            Connection connection,
            String tableName,
            String columnName
    ) throws SQLException {

        String sql = "PRAGMA table_info(" + tableName + ")";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                if (columnName.equalsIgnoreCase(
                        resultSet.getString("name")
                )) {
                    return true;
                }
            }
        }

        return false;
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

    private static void createOrdersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS orders (
                    order_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    account_id INTEGER,
                    date DATE NOT NULL,
                    total REAL NOT NULL,
                    status TEXT NOT NULL,
                    delivery_type TEXT NOT NULL,
                    actions TEXT NULL,
                    CONSTRAINT Account_ID_FK
                        FOREIGN KEY (account_id)
                        REFERENCES accounts(account_id)
                );
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);
            System.out.println("Orders table ready.");

        } catch (SQLException e) {
            System.out.println("Error creating orders table.");
            e.printStackTrace();
        }
    }

    private static void migrateOrdersTotalToReal() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet =
                     statement.executeQuery("PRAGMA table_info(orders)")) {

            boolean totalNeedsMigration = false;

            while (resultSet.next()) {
                String columnName = resultSet.getString("name");
                String columnType = resultSet.getString("type");

                if ("total".equalsIgnoreCase(columnName)
                        && !"REAL".equalsIgnoreCase(columnType)) {
                    totalNeedsMigration = true;
                    break;
                }
            }

            if (!totalNeedsMigration) {
                return;
            }

            connection.setAutoCommit(false);

            try (Statement migrationStatement =
                         connection.createStatement()) {

                migrationStatement.execute(
                        "ALTER TABLE orders RENAME TO orders_old"
                );

                migrationStatement.execute("""
                        CREATE TABLE orders (
                            order_id INTEGER PRIMARY KEY AUTOINCREMENT,
                            account_id INTEGER,
                            date DATE NOT NULL,
                            total REAL NOT NULL,
                            status TEXT NOT NULL,
                            delivery_type TEXT NOT NULL,
                            actions TEXT NULL,
                            CONSTRAINT Account_ID_FK
                                FOREIGN KEY (account_id)
                                REFERENCES accounts(account_id)
                        );
                        """);

                migrationStatement.execute("""
                        INSERT INTO orders (
                            order_id,
                            account_id,
                            date,
                            total,
                            status,
                            delivery_type,
                            actions
                        )
                        SELECT
                            order_id,
                            account_id,
                            date,
                            CAST(total AS REAL),
                            status,
                            delivery_type,
                            actions
                        FROM orders_old
                        """);

                migrationStatement.execute(
                        "DROP TABLE orders_old"
                );

                connection.commit();
                System.out.println(
                        "Orders total column migrated to REAL."
                );

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error migrating orders total column."
            );
            e.printStackTrace();
        }
    }
}