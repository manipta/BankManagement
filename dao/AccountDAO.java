package dao;

import model.Account;
import java.util.List;

public interface AccountDAO {
    void addAccount(Account account);
    Account getAccountById(int id);
    List<Account> getAllAccounts();
    void updateAccountBalance(int id, double balance);
}
