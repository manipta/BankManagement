package service;

import dao.CustomerDAO;
import model.Customer;
import java.util.List;

public class CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public int addCustomer(String name, String email) {
        Customer customer = new Customer(0, name, email);
        return customerDAO.addCustomer(customer); // returns the customer ID
    }

    public Customer getCustomerById(int id) {
        return customerDAO.getCustomerById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public void updateCustomer(Customer customer) {
        customerDAO.updateCustomer(customer);
    }
}
