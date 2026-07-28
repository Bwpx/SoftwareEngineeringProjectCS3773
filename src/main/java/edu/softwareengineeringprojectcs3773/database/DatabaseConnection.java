package edu.softwareengineeringprojectcs3773.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DEFAULT_DATABASE_URL =
            "jdbc:sqlite:data/grocery.db";

    private static final String DATABASE_URL_PROPERTY =
            "roadrunner.database.url";

    private DatabaseConnection() {
        // Utility class
    }

    public static Connection getConnection() throws SQLException {
        String databaseUrl = System.getProperty(
                DATABASE_URL_PROPERTY,
                DEFAULT_DATABASE_URL
        );

        createParentDirectory(databaseUrl);

        return DriverManager.getConnection(databaseUrl);
    }

    private static void createParentDirectory(String databaseUrl) {
        String prefix = "jdbc:sqlite:";

        if (!databaseUrl.startsWith(prefix)) {
            return;
        }

        String databasePath = databaseUrl.substring(prefix.length());

        // In-memory databases do not need a directory.
        if (databasePath.equals(":memory:")) {
            return;
        }

        Path path = Paths.get(databasePath);
        Path parent = path.getParent();

        if (parent == null) {
            return;
        }

        try {
            Files.createDirectories(parent);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not create database folder",
                    e
            );
        }
    }
}