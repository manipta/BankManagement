package model;

public class Account {
    private int id;
    private int customerId;
    private double balance;

    public Account(int id, int customerId, double balance) {
        this.id = id;
        this.customerId = customerId;
        this.balance = balance;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}
