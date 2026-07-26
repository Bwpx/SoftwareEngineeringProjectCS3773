package edu.softwareengineeringprojectcs3773.model;

public class Account {
    private int accountId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;

    public Account(int accountId, String username, String firstName, String lastName, String email, String password, String phoneNumber) {
        this.accountId = accountId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public int getAccountId() {
        return accountId;
    }
    
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }
    
    public String getFirstName() {
    	return firstName;
    }
    
    public String getLastName() {
    	return lastName;
    }
    
    public String getName() {
    	return firstName + " " + lastName;
    }
    
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setFirstName(String firstName) {
    	this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
    	this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean emailMatches(String email) {
        return this.email.equalsIgnoreCase(email);
    }

    public boolean usernameMatches(String username) {
        return this.username.equalsIgnoreCase(username);
    }

    @Override
    public String toString() {
        return "Account ID: " + accountId +
                "\nUsername: " + username +
                "\nName: " + getName() +
                "\nEmail: " + email +
                "\nPhone Number: " + phoneNumber;
    }
}