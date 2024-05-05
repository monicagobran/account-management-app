package com.github.bankapp.dto;

public class BalanceResponse {
    private String message;
    private double balance;

    public BalanceResponse(double balance) {
        this.balance = balance;
        this.message = "Balance retrieved successfully.";
    }

    public BalanceResponse(double balance, String message) {
        this.balance = balance;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
