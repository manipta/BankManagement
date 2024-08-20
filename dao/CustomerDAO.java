package dao;

import model.Customer;
import java.util.List;

public interface CustomerDAO {
    int addCustomer(Customer customer);
    Customer getCustomerById(int id);
    List<Customer> getAllCustomers();
    void updateCustomer(Customer customer);
}
