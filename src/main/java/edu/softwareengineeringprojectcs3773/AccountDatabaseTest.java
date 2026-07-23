package edu.softwareengineeringprojectcs3773;

import edu.softwareengineeringprojectcs3773.database.DatabaseInitializer;
import edu.softwareengineeringprojectcs3773.model.Account;
import edu.softwareengineeringprojectcs3773.service.AccountService;

public class AccountDatabaseTest {

    public static void main(String[] args) {
        DatabaseInitializer.initializeDatabase();

        AccountService accountService = new AccountService();

        boolean registered = accountService.registerAccount(
                "martin",
                "martin@example.com",
                "password123",
                "210-555-1234"
        );

        System.out.println("Account registered: " + registered);

        Account loggedInAccount = accountService.login(
                "martin@example.com",
                "password123"
        );

        if (loggedInAccount != null) {
            System.out.println("Login successful.");
            System.out.println(loggedInAccount);
        } else {
            System.out.println("Login failed.");
        }
    }
}
