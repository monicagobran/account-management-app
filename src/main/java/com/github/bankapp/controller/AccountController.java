package com.github.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.bankapp.dto.AccountResponse;
import com.github.bankapp.dto.BalanceResponse;
import com.github.bankapp.dto.ErrorResponse;
import com.github.bankapp.dto.TransactionRequest;
import com.github.bankapp.dto.TransactionResponse;
import com.github.bankapp.service.AccountService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@RestController
@Validated
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<?> openAccount(){
        try{
            String accountId = accountService.openAccount();
            AccountResponse response = new AccountResponse(accountId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch(Exception e){
            ErrorResponse response = new ErrorResponse("Failed to create account: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<?> depositFunds(
        @PathVariable @Pattern(regexp="[a-fA-F0-9]{8}", message="account ID is invalid")  String accountId, 
        @RequestBody @Valid TransactionRequest transactionRequest) {
        try {
            Long transactionId = accountService.deposit(accountId, transactionRequest.getAmount());
            TransactionResponse response = new TransactionResponse(transactionId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse response = new ErrorResponse("Failed to deposit funds: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<?> withdrawFunds(
        @PathVariable @Pattern(regexp="[a-fA-F0-9]{8}", message="account ID is invalid") String accountId, 
        @RequestBody @Valid TransactionRequest transactionRequest) {
        try {
            Long transactionId = accountService.withdraw(accountId, transactionRequest.getAmount());
            TransactionResponse response = new TransactionResponse(transactionId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse response = new ErrorResponse("Failed to withdraw funds: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<?> checkBalance(@PathVariable @Pattern(regexp="[a-fA-F0-9]{8}", message="account ID is invalid") String accountId){
        try {
            double balance = accountService.checkBalance(accountId);
            BalanceResponse response = new BalanceResponse(balance);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse response = new ErrorResponse("Failed to fetch balance: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
