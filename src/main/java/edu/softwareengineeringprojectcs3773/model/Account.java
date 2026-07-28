package edu.softwareengineeringprojectcs3773.model;

public class Account {
    private int accountId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;

    public Account(
            int accountId,
            String firstName,
            String lastName,
            String username,
            String email,
            String password,
            String phoneNumber
    ) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // Keeps older code compatible.
    public Account(
            int accountId,
            String username,
            String email,
            String password,
            String phoneNumber
    ) {
        this(
                accountId,
                null,
                null,
                username,
                email,
                password,
                phoneNumber
        );
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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
        return "Account ID: " + accountId
                + "\nFirst Name: " + firstName
                + "\nLast Name: " + lastName
                + "\nUsername: " + username
                + "\nEmail: " + email
                + "\nPhone Number: " + phoneNumber;
    }
}
