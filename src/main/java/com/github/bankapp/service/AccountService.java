package com.github.bankapp.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.bankapp.entity.Account;
import com.github.bankapp.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionService transactionService;

    public String openAccount(){
        String accountId = generateAccountId();
        Account account = new Account(accountId);
        accountRepository.save(account);
        return accountId;
    } 

    private String generateAccountId(){
        String accountId;
        do {
            accountId = UUID.randomUUID().toString().substring(0,8);
        }
        while(accountRepository.findByAccountId(accountId) != null);    
        
        return accountId;
    }

    public Long deposit(String accountId, double amount){
        // retrieve and validate account
        Account account = accountRepository.findByAccountId(accountId);
        if (account == null){
            throw new IllegalArgumentException("Account not found");
        }
        // validate amount
        if (amount <= 0.0){
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        // create deposit transaction
        Long transactionId = transactionService.deposit(accountId, amount);
        // update balance
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        return transactionId;
    }
}
