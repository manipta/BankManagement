package dao;

import model.Customer;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    private static final String INSERT_CUSTOMER_SQL = "INSERT INTO customers (name, email) VALUES (?, ?)";
    @Override
    public int addCustomer(Customer customer) {
        int customerId = -1;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CUSTOMER_SQL,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        customerId = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
//            e.getSQLState
//            e.printStackTrace();  // Handle exception properly
        }
        return customerId;
    }

    @Override
    public Customer getCustomerById(int id) {
        String query = "SELECT * FROM customers WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching customer", e);
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                customers.add(new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching customers", e);
        }
        return customers;
    }

    @Override
    public void updateCustomer(Customer customer) {
        String query = "UPDATE customers SET name = ?, email = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setInt(3, customer.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating customer", e);
        }
    }
}
