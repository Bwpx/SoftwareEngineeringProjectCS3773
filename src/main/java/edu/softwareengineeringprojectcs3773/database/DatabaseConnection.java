package edu.softwareengineeringprojectcs3773.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DATABASE_URL = "jdbc:sqlite:data/grocery.db";

    public static Connection getConnection() throws SQLException {
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (IOException e) {
            throw new RuntimeException("Could not create data folder", e);
        }

        return DriverManager.getConnection(DATABASE_URL);
    }
}