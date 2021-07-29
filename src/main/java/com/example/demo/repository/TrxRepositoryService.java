package com.example.demo.repository;

import com.example.demo.entity.Trx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrxRepositoryService {

    @Autowired
    TrxRepository trxRepository;

    public Trx findByInquiryCode(String inquiryCode) {
        return trxRepository.findByInquiryCode(inquiryCode);
    }

    public void save(Trx trx) {
        trxRepository.save(trx);
    }
}
