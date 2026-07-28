package edu.softwareengineeringprojectcs3773.repository;

import edu.softwareengineeringprojectcs3773.database.TestDatabase;
import edu.softwareengineeringprojectcs3773.model.Account;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryTest {

    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        TestDatabase.reset();
        accountRepository = new AccountRepository();
    }

    @AfterAll
    static void tearDownAll() {
        TestDatabase.deleteTestDatabase();
        TestDatabase.stopUsingTestDatabase();
    }

    @Test
    void saveAssignsGeneratedAccountId() {
        Account account = createAccount(
                "martin123",
                "martin@example.com"
        );

        Account savedAccount = accountRepository.save(account);

        assertNotNull(savedAccount);
        assertTrue(savedAccount.getAccountId() > 0);
    }

    @Test
    void findByEmailReturnsSavedAccountCaseInsensitively() {
        Account savedAccount = accountRepository.save(
                createAccount(
                        "martin123",
                        "martin@example.com"
                )
        );

        Account foundAccount =
                accountRepository.findByEmail("MARTIN@EXAMPLE.COM");

        assertNotNull(foundAccount);
        assertEquals(
                savedAccount.getAccountId(),
                foundAccount.getAccountId()
        );
        assertEquals(
                "martin@example.com",
                foundAccount.getEmail()
        );
    }

    @Test
    void findByUsernameReturnsSavedAccountCaseInsensitively() {
        Account savedAccount = accountRepository.save(
                createAccount(
                        "martin123",
                        "martin@example.com"
                )
        );

        Account foundAccount =
                accountRepository.findByUsername("MARTIN123");

        assertNotNull(foundAccount);
        assertEquals(
                savedAccount.getAccountId(),
                foundAccount.getAccountId()
        );
        assertEquals(
                "martin123",
                foundAccount.getUsername()
        );
    }

    @Test
    void updateAccountChangesStoredInformation() {
        Account savedAccount = accountRepository.save(
                createAccount(
                        "martin123",
                        "martin@example.com"
                )
        );

        Account updatedAccount = new Account(
                savedAccount.getAccountId(),
                "Martin",
                "Gonzalez",
                "updatedMartin",
                "updated@example.com",
                "newPassword",
                "2105559999"
        );

        Account result =
                accountRepository.updateAccount(updatedAccount);

        Account foundAccount =
                accountRepository.findById(
                        savedAccount.getAccountId()
                );

        assertNotNull(result);
        assertNotNull(foundAccount);
        assertEquals(
                "updatedMartin",
                foundAccount.getUsername()
        );
        assertEquals(
                "updated@example.com",
                foundAccount.getEmail()
        );
        assertEquals(
                "newPassword",
                foundAccount.getPassword()
        );
        assertEquals(
                "2105559999",
                foundAccount.getPhoneNumber()
        );
    }

    @Test
    void findAllReturnsEverySavedAccount() {
        accountRepository.save(
                createAccount(
                        "martin123",
                        "martin@example.com"
                )
        );

        accountRepository.save(
                new Account(
                        0,
                        "John",
                        "Smith",
                        "john123",
                        "john@example.com",
                        "password456",
                        "2105555678"
                )
        );

        ArrayList<Account> accounts =
                accountRepository.findAll();

        assertEquals(2, accounts.size());
        assertEquals("martin123", accounts.get(0).getUsername());
        assertEquals("john123", accounts.get(1).getUsername());
    }

    private Account createAccount(
            String username,
            String email
    ) {
        return new Account(
                0,
                "Martin",
                "Gonzalez",
                username,
                email,
                "password123",
                "2105551234"
        );
    }
}
