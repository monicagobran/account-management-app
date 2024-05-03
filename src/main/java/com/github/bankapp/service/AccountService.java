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

    public String openAccount(){
        String accountId = UUID.randomUUID().toString();
        Account account = new Account(accountId);
        accountRepository.save(account);
        return accountId;
    } 

}
