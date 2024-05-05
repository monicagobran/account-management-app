package com.github.bankapp.dto;

public class AccountResponse {
    private String message;
    private String accountId;

    public AccountResponse(String accountId, String message) {
        this.accountId = accountId;
        this.message = message;
    }

    public AccountResponse(String accountId) {
        this.accountId = accountId;
        this.message = "Account created successfully";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    

}
