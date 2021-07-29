package com.example.demo.module.transaction;

import com.example.demo.module.transaction.request.InquiryRequest;
import com.example.demo.module.transaction.request.PaymentRequest;
import com.example.demo.module.transaction.response.InquiryResponse;
import com.example.demo.module.transaction.response.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService service;

    @PostMapping("/inquiry")
    public InquiryResponse inquiry(@RequestBody @Valid InquiryRequest request) {
        return service.inquiry(request);
    }

    @PostMapping("/payment")
    public PaymentResponse inquiry(@RequestBody @Valid PaymentRequest request) {
        return service.payment(request);
    }
}
