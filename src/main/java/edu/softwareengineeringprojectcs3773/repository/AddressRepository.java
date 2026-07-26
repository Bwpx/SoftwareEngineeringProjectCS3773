package edu.softwareengineeringprojectcs3773.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.softwareengineeringprojectcs3773.database.DatabaseConnection;
import edu.softwareengineeringprojectcs3773.model.Account;
import edu.softwareengineeringprojectcs3773.model.Address;
import edu.softwareengineeringprojectcs3773.model.Order;

public class AddressRepository {
	
	public Address save(Address address) {
		String sql = """
				INSERT INTO addresses (account_id, line_1, line_2, city, state, zip, type, autofill)
				VALUES (?, ?, ?, ?, ?, ?, ?, ?)
				""";
		
		try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(
	                     sql,
	                     Statement.RETURN_GENERATED_KEYS
	             )) {

	            statement.setInt(1, address.getAccountId());
	            statement.setString(2, address.getLine1());
	            statement.setString(3, address.getLine2());
	            statement.setString(4, address.getCity());
	            statement.setString(5, address.getState());
	            statement.setInt(6, address.getZip());
	            statement.setString(7, address.getType());
	            statement.setBoolean(8, address.getAutofill());

	            statement.executeUpdate();

	            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    address.setAddressId(generatedKeys.getInt(1));
	                }
	            }

	            return address;

	        } catch (SQLException e) {
	            System.out.println("Error saving order.");
	            e.printStackTrace();
	            return null;
	        }
	}
	
    public Address updateAddress(Address address) {
    	String sql = """
    			UPDATE addresses
    			SET account_id = ?,
    				line_1 = ?,
    				line_2 = ?,
    				city = ?,
    				state = ?,
    				zip = ?,
    				type = ?,
    				autofill = ?
    			WHERE address_id = ?
    			""";
    	
    	try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

    			statement.setInt(1, address.getAccountId());
    			statement.setString(2, address.getLine1());
    			statement.setString(3, address.getLine2());
    			statement.setString(4, address.getCity());
    			statement.setString(5, address.getState());
    			statement.setInt(6, address.getZip());
    			statement.setString(7, address.getType());
    			statement.setBoolean(8, address.getAutofill());
    			statement.setInt(9, address.getAddressId());

               int rowsUpdated = statement.executeUpdate();
               
               if(rowsUpdated > 0) {
            	   return address;
               }

           } catch (SQLException e) {
               System.out.println("Error updating address.");
               e.printStackTrace();
           }

           return null;
    }
	
	public Address findById(int addressId) {
		String sql = """
				SELECT address_id, account_id, line_1, line_2, city, state, zip, type, autofill
				FROM addresses
				WHERE address_id = ?
				""";
		try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(sql)) {

	            statement.setInt(1, addressId);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    return createAddressFromResultSet(resultSet);
	                }
	            }

	        } catch (SQLException e) {
	            System.out.println("Error finding address by ID.");
	            e.printStackTrace();
	        }

	        return null;
	}
	
	
	public ArrayList<Address> findByAccountId(int accountId) {
		String sql = """
				SELECT address_id, account_id, line_1, line_2, city, state, zip, type, autofill
				FROM addresses
				WHERE account_id = ?
				""";
		ArrayList<Address> addresses = new ArrayList<>();
		try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(sql)) {

	            statement.setInt(1, accountId);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    addresses.add(createAddressFromResultSet(resultSet));
	                }
	            }

	        } catch (SQLException e) {
	            System.out.println("Error finding address by account ID.");
	            e.printStackTrace();
	        }

	        return addresses;
	}
	
	public Address findDefaultbyAccountId(int accountId) {
		String sql = """
				SELECT address_id, account_id, line_1, line_2, city, state, zip, type, autofill
				FROM addresses
				WHERE account_id = ?
				AND autofill > 0
				""";
		try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(sql)) {

	            statement.setInt(1, accountId);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    return createAddressFromResultSet(resultSet);
	                }
	            }

	        } catch (SQLException e) {
	            System.out.println("Error finding default address by account ID.");
	            e.printStackTrace();
	        }

	        return null;
	}
	
	public Address createAddressFromResultSet(ResultSet resultSet) throws SQLException {
		return new Address(
				resultSet.getInt("address_id"),
				resultSet.getInt("account_id"),
				resultSet.getString("line_1"),
				resultSet.getString("line_2"),
				resultSet.getString("city"),
				resultSet.getString("state"),
				resultSet.getInt("zip"),
				resultSet.getString("type"),
				resultSet.getBoolean("autofill")
		);
	}
}
