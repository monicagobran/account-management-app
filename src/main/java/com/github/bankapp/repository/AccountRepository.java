package com.github.bankapp.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.github.bankapp.entity.Account;

@Repository
public class AccountRepository {

    private Map<String, Account> accounts = new HashMap<>();

    public void save(Account account){
        accounts.put(account.getAccountId(), account);
    }

    public Account findByAccountId(String accountId){
        return accounts.get(accountId);
    }

    public void updateBalance(String accountId, double balance){
        Account account = accounts.get(accountId);
        if (account != null) {
            account.setBalance(balance);
            accounts.put(accountId, account);
        }
    }

}
