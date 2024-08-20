import dao.CustomerDAO;
import dao.CustomerDAOImpl;
import dao.AccountDAO;
import dao.AccountDAOImpl;
import dao.TransactionDAO;
import dao.TransactionDAOImpl;
import model.Admin;
import service.AdminService;
import service.CustomerService;
import service.AccountService;
import service.TransactionService;
import model.Customer;
import model.Account;
import model.Transaction;
import util.SecurityUtil;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.List;

public class Main {
    private static final AdminService adminService = new AdminService();
    private static final Scanner scanner = new Scanner(System.in);
    private static final CustomerService customerService = new CustomerService(new CustomerDAOImpl());
    private static final AccountService accountService = new AccountService(new AccountDAOImpl());
    private static final TransactionService transactionService = new TransactionService(new TransactionDAOImpl());

    private static Admin loggedInAdmin = null;

    public static void main(String[] args) {
        while(true){
            if(loggedInAdmin == null){
                adminLogin();
            }
            else {
                displayMenu();
            }
        }
    }
    private static void adminLogin() {
        System.out.println("===== Admin Login =====");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            Admin admin = adminService.loginAdmin(username, password);
            if (admin != null) {
                loggedInAdmin = admin;
                System.out.println("Login successful. Welcome, " + admin.getUsername() + "!");
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private static void displayMenu() {
        System.out.println("===== Bank Management System =====");
        System.out.println("1. Add Customer");
        System.out.println("2. View Customer Details");
        System.out.println("3. Create Account");
        System.out.println("4. View Account Details");
        System.out.println("5. Deposit");
        System.out.println("6. Withdraw");
        System.out.println("7. View Transactions");
        System.out.println("8. Logout");
        System.out.print("Select an option: ");
        int option = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (option) {
            case 1:
                addCustomer();
                break;
            case 2:
                viewCustomerDetails();
                break;
            case 3:
                createAccount();
                break;
            case 4:
                viewAccountDetails();
                break;
            case 5:
                deposit();
                break;
            case 6:
                withdraw();
                break;
            case 7:
                viewTransactions();
                break;
            case 8:
                System.out.println("Exiting....");
                loggedInAdmin = null;
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                return;
        }
    }
    private static void addCustomer() {
        System.out.println("===== Add Customer =====");
        System.out.print("Enter customer name: ");
        String name = SecurityUtil.sanitizeInput(scanner.nextLine());
        System.out.print("Enter customer email: ");
        String email = SecurityUtil.sanitizeInput(scanner.nextLine());

        Customer customer = new Customer(0, name, email);
        int customerId = customerService.addCustomer(name, email);
        System.out.println("Customer added successfully with ID: " + customerId);
    }

    private static void viewCustomerDetails() {
        System.out.println("===== View Customer Details =====");
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Customer customer = customerService.getCustomerById(customerId);
        if (customer != null) {
            System.out.println("Customer ID: " + customer.getId());
            System.out.println("Customer Name: " + customer.getName());
            System.out.println("Customer Email: " + customer.getEmail());
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void createAccount() {
        System.out.println("===== Create Account =====");
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        Account account = new Account(0, customerId, balance);
        accountService.addAccount(account);
        System.out.println("Account created successfully. " + account.getId());
    }

    private static void viewAccountDetails() {
        System.out.println("===== View Account Details =====");
        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Account account = accountService.getAccountById(accountId);
        if (account != null) {
            System.out.println("Account ID: " + account.getId());
            System.out.println("Customer ID: " + account.getCustomerId());
            System.out.println("Balance: $" + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void deposit() {
        System.out.println("===== Deposit =====");
        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        try {
            accountService.deposit(accountId, amount);
            transactionService.addTransaction(new Transaction(0, accountId, "Deposit", amount, new java.sql.Timestamp(System.currentTimeMillis())));
            System.out.println("Deposit successful.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void withdraw() {
        System.out.println("===== Withdraw =====");
        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        try {
            accountService.withdraw(accountId, amount);
            transactionService.addTransaction(new Transaction(0, accountId, "Withdraw", amount, new java.sql.Timestamp(System.currentTimeMillis())));
            System.out.println("Withdrawal successful.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewTransactions() {
        System.out.println("===== View Transactions =====");
        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println("Transaction ID: " + transaction.getId());
                System.out.println("Type: " + transaction.getType());
                System.out.println("Amount: $" + transaction.getAmount());
                System.out.println("Date: " + transaction.getDate());
                System.out.println("-----------------------------");
            }
        }
    }
}
