package edu.softwareengineeringprojectcs3773.repository;

import edu.softwareengineeringprojectcs3773.model.Account;
import java.util.ArrayList;

public class AccountRepository {
    private ArrayList<Account> accounts;

    public AccountRepository() {
        accounts = new ArrayList<>();
    }

    public void save(Account account) {
        accounts.add(account);
    }

    public Account findByEmail(String email) {
        for (Account account : accounts) {
            if (account.emailMatches(email)) {
                return account;
            }
        }

        return null;
    }

    public Account findByUsername(String username) {
        for (Account account : accounts) {
            if (account.usernameMatches(username)) {
                return account;
            }
        }

        return null;
    }

    public Account findById(int accountId) {
        for (Account account : accounts) {
            if (account.getAccountId() == accountId) {
                return account;
            }
        }

        return null;
    }

    public ArrayList<Account> findAll() {
        return accounts;
    }

    public boolean emailExists(String email) {
        return findByEmail(email) != null;
    }

    public boolean usernameExists(String username) {
        return findByUsername(username) != null;
    }
}
