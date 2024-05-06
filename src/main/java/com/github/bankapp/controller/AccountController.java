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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@Tag(name = "Account", description = "Account management APIs")
@RestController
@Validated
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Operation(summary = "Create a new account", tags = { "Account"})
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = AccountResponse.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "500", content = { 
            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json") }) })
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

    @Operation(summary = "Deposit funds into account", tags = { "Account"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = TransactionResponse.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "400", content = { 
            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json") }) })
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

    @Operation(summary = "Withdraw funds from account", tags = { "Account"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = TransactionResponse.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "400", content = { 
            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json") }) })
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


    @Operation(summary = "Check account balance", tags = { "Account"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = BalanceResponse.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "400", content = { 
            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json") }) })
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
