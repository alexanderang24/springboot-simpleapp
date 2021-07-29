package com.example.demo.module.trx;

import com.example.demo.entity.Trx;
import com.example.demo.module.trx.request.InquiryRequest;
import com.example.demo.module.trx.request.PaymentRequest;
import com.example.demo.module.trx.response.InquiryResponse;
import com.example.demo.module.trx.response.PaymentResponse;
import com.example.demo.repository.TrxRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TrxService {

    public static final String SUCCESS = "SUCCESS";
    public static final String COMPLETED = "COMPLETED";
    public static final String FAILED = "FAILED";

    @Autowired
    TrxRepositoryService trxRepositoryService;

    public InquiryResponse inquiry(InquiryRequest request) {
        log.info("Received inquiry with inquiry code: " + request.getInquiryCode());

        String status;
        Trx trx = trxRepositoryService.findByInquiryCode(request.getInquiryCode());
        if (null == trx) {
            log.info("Creating new transaction");
            trx = Trx.builder()
                    .productName(request.getProductName())
                    .amount(request.getAmount())
                    .inquiryCode(request.getInquiryCode())
                    .trxStatus("INQUIRED")
                    .build();
            status = SUCCESS;
        } else if (trx.getTrxStatus().equals(COMPLETED)) {
            log.info("Transaction is already completed!");
            status = FAILED;
        } else {
            log.info("Updating existing transaction");
            trx.setProductName(request.getProductName());
            trx.setAmount(request.getAmount());
            status = SUCCESS;
        }
        trxRepositoryService.save(trx);

        return InquiryResponse.builder()
                .status(status)
                .build();
    }

    public PaymentResponse payment(PaymentRequest request) {
        log.info("Received payment with inquiry code: " + request.getInquiryCode());
        String status;
        Trx trx = trxRepositoryService.findByInquiryCode(request.getInquiryCode());

        if (null == trx) {
            log.info("Found no transaction with specified inquiry code!");
            status = FAILED;
        } else if (trx.getTrxStatus().equals(COMPLETED)) {
            log.info("Transaction is already completed!");
            status = FAILED;
        } else {
            log.info("Transaction completed");
            trx.setTrxStatus(COMPLETED);
            trxRepositoryService.save(trx);
            status = SUCCESS;
        }

        return PaymentResponse.builder()
                .status(status)
                .build();
    }
}
