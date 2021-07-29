package com.example.demo.repository;

import com.example.demo.entity.Trx;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrxRepository extends CrudRepository<Trx, Long> {

    Trx findByInquiryCode(String inquiryCode);
}
