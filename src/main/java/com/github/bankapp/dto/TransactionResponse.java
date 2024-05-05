package com.github.bankapp.dto;

public class TransactionResponse {
    private String message;
    private Long transactionId;

    public TransactionResponse(String message, Long transactionId) {
        this.message = message;
        this.transactionId = transactionId;
    }

    public TransactionResponse(Long transactionId) {
        this.transactionId = transactionId;
        this.message = "Transaction completed successfully";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

}
