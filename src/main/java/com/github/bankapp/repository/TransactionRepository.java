package com.github.bankapp.repository;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Repository;

import com.github.bankapp.entity.Transaction;

@Repository
public class TransactionRepository {
    private List<Transaction> transactions = new ArrayList<>();

    public void save(Transaction transaction){
        transactions.add(transaction);
    }

}
