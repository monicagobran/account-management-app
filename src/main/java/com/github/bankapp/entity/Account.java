package com.github.bankapp.entity;

public class Account {
    private String accountId;
    private double balance;

    public Account() {
        this.balance = 0.0;
    }

    public Account(String accountId){
        super();
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

}
