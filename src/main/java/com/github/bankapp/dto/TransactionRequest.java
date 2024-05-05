package com.github.bankapp.dto;

import jakarta.validation.constraints.DecimalMin;

public class TransactionRequest {
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
