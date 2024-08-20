package service;

import dao.TransactionDAO;
import model.Transaction;
import java.util.List;

public class TransactionService {
    private final TransactionDAO transactionDAO;

    public TransactionService(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    public void addTransaction(Transaction transaction) {
        transactionDAO.addTransaction(transaction);
    }

    public List<Transaction> getTransactionsByAccountId(int accountId) {
        return transactionDAO.getTransactionsByAccountId(accountId);
    }
}
