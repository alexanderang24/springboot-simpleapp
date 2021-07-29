package com.example.demo.repository;

import com.example.demo.entity.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionRepositoryService {

    @Autowired
    TransactionRepository transactionRepository;

    public Transaction findByInquiryCode(String inquiryCode) {
        log.debug("Finding transaction by inquiry code: [" + inquiryCode + "]");
        return transactionRepository.findByInquiryCode(inquiryCode);
    }
    public void save(Transaction transaction) {
        log.debug("Saving transaction with inquiry code: [" + transaction.getInquiryCode() + "]");
        transactionRepository.save(transaction);
    }
}
