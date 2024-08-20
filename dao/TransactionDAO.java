package dao;

import model.Transaction;
import java.util.List;

public interface TransactionDAO {
    void addTransaction(Transaction transaction);
    List<Transaction> getTransactionsByAccountId(int accountId);
}
