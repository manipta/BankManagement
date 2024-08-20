package dao;

import model.Account;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public void addAccount(Account account) {
        String query = "INSERT INTO accounts (customer_id, balance) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, account.getCustomerId());
            stmt.setDouble(2, account.getBalance());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding account", e);
        }
    }

    @Override
    public Account getAccountById(int id) {
        String query = "SELECT * FROM accounts WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Account(rs.getInt("id"), rs.getInt("customer_id"), rs.getDouble("balance"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching account", e);
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT * FROM accounts";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                accounts.add(new Account(rs.getInt("id"), rs.getInt("customer_id"), rs.getDouble("balance")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching accounts", e);
        }
        return accounts;
    }

    @Override
    public void updateAccountBalance(int id, double balance) {
        String query = "UPDATE accounts SET balance = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, balance);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating account balance", e);
        }
    }
}
