package dao;

import model.Transaction;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {

    @Override
    public void addTransaction(Transaction transaction) {
        String query = "INSERT INTO transactions (account_id, type, amount, date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, transaction.getAccountId());
            stmt.setString(2, transaction.getType());
            stmt.setDouble(3, transaction.getAmount());
            stmt.setTimestamp(4, transaction.getDate());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding transaction", e);
        }
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(int accountId) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE account_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(new Transaction(rs.getInt("id"), rs.getInt("account_id"), rs.getString("type"), rs.getDouble("amount"), rs.getTimestamp("date")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching transactions", e);
        }
        return transactions;
    }
}
