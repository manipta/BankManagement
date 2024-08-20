package service;

import dao.AccountDAO;
import model.Account;
import java.util.List;

public class AccountService  {
    private final AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public void addAccount(Account account) {
        accountDAO.addAccount(account);
    }

    public Account getAccountById(int id) {
        return accountDAO.getAccountById(id);
    }

    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    public void deposit(int accountId, double amount) {
        Account account = accountDAO.getAccountById(accountId);
        if (account != null) {
            double newBalance = account.getBalance() + amount;
            accountDAO.updateAccountBalance(accountId, newBalance);
        } else {
            throw new RuntimeException("Account not found");
        }
    }

    public void withdraw(int accountId, double amount) {
        Account account = accountDAO.getAccountById(accountId);
        if (account != null) {
            double newBalance = account.getBalance() - amount;
            if (newBalance < 0) {
                throw new RuntimeException("Insufficient funds");
            }
            accountDAO.updateAccountBalance(accountId, newBalance);
        } else {
            throw new RuntimeException("Account not found");
        }
    }
}
