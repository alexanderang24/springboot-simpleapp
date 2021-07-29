package com.example.demo.repository;

import com.example.demo.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionRepositoryService {

    @Autowired
    TransactionRepository transactionRepository;

    public Transaction findByInquiryCode(String inquiryCode) {
        return transactionRepository.findByInquiryCode(inquiryCode);
    }
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
