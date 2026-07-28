package edu.softwareengineeringprojectcs3773.service;

import edu.softwareengineeringprojectcs3773.database.TestDatabase;
import edu.softwareengineeringprojectcs3773.model.Account;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        TestDatabase.reset();
        accountService = new AccountService();
    }

    @AfterAll
    static void tearDownAll() {
        TestDatabase.deleteTestDatabase();
        TestDatabase.stopUsingTestDatabase();
    }

    @Test
    void registerValidAccountReturnsTrue() {
        boolean result = accountService.registerAccount(
                "Martin",
                "Gonzalez",
                "martin123",
                "martin@example.com",
                "password123",
                "2105551234"
        );

        assertTrue(result);
    }

    @Test
    void registerWithBlankFirstNameReturnsFalse() {
        boolean result = accountService.registerAccount(
                "   ",
                "Gonzalez",
                "martin123",
                "martin@example.com",
                "password123",
                "2105551234"
        );

        assertFalse(result);
    }

    @Test
    void registerWithBlankLastNameReturnsFalse() {
        boolean result = accountService.registerAccount(
                "Martin",
                "",
                "martin123",
                "martin@example.com",
                "password123",
                "2105551234"
        );

        assertFalse(result);
    }

    @Test
    void registerWithBlankUsernameReturnsFalse() {
        boolean result = accountService.registerAccount(
                "Martin",
                "Gonzalez",
                " ",
                "martin@example.com",
                "password123",
                "2105551234"
        );

        assertFalse(result);
    }

    @Test
    void registerWithBlankEmailReturnsFalse() {
        boolean result = accountService.registerAccount(
                "Martin",
                "Gonzalez",
                "martin123",
                "",
                "password123",
                "2105551234"
        );

        assertFalse(result);
    }

    @Test
    void registerWithBlankPasswordReturnsFalse() {
        boolean result = accountService.registerAccount(
                "Martin",
                "Gonzalez",
                "martin123",
                "martin@example.com",
                " ",
                "2105551234"
        );

        assertFalse(result);
    }

    @Test
    void duplicateUsernameReturnsFalse() {
        assertTrue(accountService.registerAccount(
                "Martin",
                "Gonzalez",
                "martin123",
                "martin1@example.com",
                "password123",
                "2105551234"
        ));

        boolean secondResult = accountService.registerAccount(
                "John",
                "Smith",
                "MARTIN123",
                "john@example.com",
                "password456",
                "2105555678"
        );

        assertFalse(secondResult);
    }

    @Test
    void duplicateEmailReturnsFalse() {
        assertTrue(accountService.registerAccount(
                "Martin",
                "Gonzalez",
                "martin123",
                "martin@example.com",
                "password123",
                "2105551234"
        ));

        boolean secondResult = accountService.registerAccount(
                "John",
                "Smith",
                "john123",
                "MARTIN@EXAMPLE.COM",
                "password456",
                "2105555678"
        );

        assertFalse(secondResult);
    }

    @Test
    void loginWithCorrectCredentialsReturnsAccount() {
        accountService.registerAccount(
                "Martin",
                "Gonzalez",
                "martin123",
                "martin@example.com",
                "password123",
                "2105551234"
        );

        Account account = accountService.login(
                "martin@example.com",
                "password123"
        );

        assertNotNull(account);
        assertEquals("martin123", account.getUsername());
        assertEquals("Martin", account.getFirstName());
        assertEquals("Gonzalez", account.getLastName());
    }

    @Test
    void loginWithWrongPasswordReturnsNull() {
        accountService.registerAccount(
                "Martin",
                "Gonzalez",
                "martin123",
                "martin@example.com",
                "password123",
                "2105551234"
        );

        Account account = accountService.login(
                "martin@example.com",
                "wrongPassword"
        );

        assertNull(account);
    }

    @Test
    void registeredAccountCanBeFoundByUsernameAndEmail() {
        accountService.registerAccount(
                "  Martin  ",
                "  Gonzalez  ",
                "  martin123  ",
                "  martin@example.com  ",
                "password123",
                "2105551234"
        );

        Account byUsername =
                accountService.findAccountByUsername("martin123");

        Account byEmail =
                accountService.findAccountByEmail("martin@example.com");

        assertNotNull(byUsername);
        assertNotNull(byEmail);

        assertEquals(byUsername.getAccountId(), byEmail.getAccountId());
        assertEquals("Martin", byUsername.getFirstName());
        assertEquals("Gonzalez", byUsername.getLastName());
        assertTrue(accountService.accountExists("martin@example.com"));
    }
}