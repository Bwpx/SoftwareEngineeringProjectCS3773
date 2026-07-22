package edu.softwareengineeringprojectcs3773.repository;

import edu.softwareengineeringprojectcs3773.database.DatabaseConnection;
import edu.softwareengineeringprojectcs3773.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AccountRepository {

    public Account save(Account account) {
        String sql = """
                INSERT INTO accounts (username, email, password, phone_number)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
             )) {

            statement.setString(1, account.getUsername());
            statement.setString(2, account.getEmail());
            statement.setString(3, account.getPassword());
            statement.setString(4, account.getPhoneNumber());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    account.setAccountId(generatedKeys.getInt(1));
                }
            }

            return account;

        } catch (SQLException e) {
            System.out.println("Error saving account.");
            e.printStackTrace();
            return null;
        }
    }

    public Account findByEmail(String email) {
        String sql = """
                SELECT account_id, username, email, password, phone_number
                FROM accounts
                WHERE LOWER(email) = LOWER(?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createAccountFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding account by email.");
            e.printStackTrace();
        }

        return null;
    }

    public Account findByUsername(String username) {
        String sql = """
                SELECT account_id, username, email, password, phone_number
                FROM accounts
                WHERE LOWER(username) = LOWER(?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createAccountFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding account by username.");
            e.printStackTrace();
        }

        return null;
    }

    public Account findById(int accountId) {
        String sql = """
                SELECT account_id, username, email, password, phone_number
                FROM accounts
                WHERE account_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, accountId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createAccountFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding account by ID.");
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Account> findAll() {
        ArrayList<Account> accounts = new ArrayList<>();

        String sql = """
                SELECT account_id, username, email, password, phone_number
                FROM accounts
                ORDER BY account_id
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                accounts.add(createAccountFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving accounts.");
            e.printStackTrace();
        }

        return accounts;
    }

    public boolean emailExists(String email) {
        return findByEmail(email) != null;
    }

    public boolean usernameExists(String username) {
        return findByUsername(username) != null;
    }

    private Account createAccountFromResultSet(ResultSet resultSet)
            throws SQLException {

        return new Account(
                resultSet.getInt("account_id"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("phone_number")
        );
    }
}
