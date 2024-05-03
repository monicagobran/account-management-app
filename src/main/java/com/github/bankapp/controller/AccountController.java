package com.github.bankapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.bankapp.dto.TransactionRequest;
import com.github.bankapp.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<?> openAccount(){
        try{
            String accountId = accountService.openAccount();
            Map<String, String> response = new HashMap<>();
		    response.put("msg", "Account created successfully");
            response.put("account_id", accountId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch(Exception e){
            Map<String, String> response = new HashMap<>();
		    response.put("msg", "Failed to create account: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<?> depositFunds(@PathVariable String accountId, @RequestBody TransactionRequest transactionRequest) {
        try {
            Long transactionId = accountService.deposit(accountId, transactionRequest.getAmount());
            Map<String, String> response = new HashMap<>();
		    response.put("msg", "Funds deposited successfully.");
            response.put("transaction_id", transactionId.toString());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
		    response.put("msg", "Failed to deposit funds: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
