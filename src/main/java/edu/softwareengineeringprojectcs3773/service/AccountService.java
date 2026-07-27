package edu.softwareengineeringprojectcs3773.service;

import edu.softwareengineeringprojectcs3773.model.Account;
import edu.softwareengineeringprojectcs3773.repository.AccountRepository;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService() {
        accountRepository = new AccountRepository();
    }

    public boolean registerAccount(
            String firstName,
            String lastName,
            String username,
            String email,
            String password,
            String phoneNumber
    ) {
        if (firstName == null || firstName.isBlank()) {
            return false;
        }

        if (lastName == null || lastName.isBlank()) {
            return false;
        }

        if (username == null || username.isBlank()) {
            return false;
        }

        if (email == null || email.isBlank()) {
            return false;
        }

        if (password == null || password.isBlank()) {
            return false;
        }

        firstName = firstName.trim();
        lastName = lastName.trim();
        username = username.trim();
        email = email.trim();

        if (accountRepository.usernameExists(username)) {
            return false;
        }

        if (accountRepository.emailExists(email)) {
            return false;
        }

        Account newAccount = new Account(
                0,
                firstName,
                lastName,
                username,
                email,
                password,
                phoneNumber
        );

        return accountRepository.save(newAccount) != null;
    }

    // Keeps older code compatible.
    public boolean registerAccount(
            String username,
            String email,
            String password,
            String phoneNumber
    ) {
        return registerAccount(
                "",
                "",
                username,
                email,
                password,
                phoneNumber
        );
    }

    public Account login(String email, String password) {
        if (email == null || email.isBlank()) {
            return null;
        }

        if (password == null || password.isEmpty()) {
            return null;
        }

        Account account = accountRepository.findByEmail(email.trim());

        if (account == null) {
            return null;
        }

        if (account.getPassword().equals(password)) {
            return account;
        }

        return null;
    }

    public Account findAccountByEmail(String email) {
        if (email == null || email.isBlank()) {
            return null;
        }

        return accountRepository.findByEmail(email.trim());
    }

    public Account findAccountByUsername(String username) {
        if (username == null || username.isBlank()) {
            return null;
        }

        return accountRepository.findByUsername(username.trim());
    }

    public boolean accountExists(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }

        return accountRepository.emailExists(email.trim());
    }
}
