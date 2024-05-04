package com.github.bankapp.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.bankapp.entity.Transaction;
import com.github.bankapp.entity.TransactionType;
import com.github.bankapp.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    private static final AtomicLong transactionIdGenerator = new AtomicLong(0);


    public Long deposit(String accountId, double amount){
        Long transactionId = generateTransactionId();
        Transaction transaction = new Transaction(transactionId, accountId, TransactionType.DEPOSIT, amount);
        transactionRepository.save(transaction);
        return transactionId;
    }

    public Long withdraw(String accountId, double amount){
        Long transactionId = generateTransactionId();
        Transaction transaction = new Transaction(transactionId, accountId, TransactionType.WITHDRAWAL, amount);
        transactionRepository.save(transaction);
        return transactionId;
    }

    private long generateTransactionId() {
        return transactionIdGenerator.incrementAndGet();
    }

}
