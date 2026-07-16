package edu.softwareengineeringprojectcs3773.service;

import edu.softwareengineeringprojectcs3773.model.Account;
import edu.softwareengineeringprojectcs3773.repository.AccountRepository;

public class AccountService {
    private AccountRepository accountRepository;
    private int nextAccountId;

    public AccountService() {
        accountRepository = new AccountRepository();
        nextAccountId = 1;
    }

    public boolean registerAccount(String username, String email, String password, String phoneNumber) {
        if (username == null || username.isEmpty()) {
            return false;
        }

        if (email == null || email.isEmpty()) {
            return false;
        }

        if (password == null || password.isEmpty()) {
            return false;
        }

        if (accountRepository.usernameExists(username)) {
            return false;
        }

        if (accountRepository.emailExists(email)) {
            return false;
        }

        Account newAccount = new Account(nextAccountId, username, email, password, phoneNumber);
        accountRepository.save(newAccount);
        nextAccountId++;

        return true;
    }

    public Account login(String email, String password) {
        Account account = accountRepository.findByEmail(email);

        if (account == null) {
            return null;
        }

        if (account.getPassword().equals(password)) {
            return account;
        }

        return null;
    }

    public Account findAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Account findAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public boolean accountExists(String email) {
        return accountRepository.emailExists(email);
    }
}
