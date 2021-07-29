package com.example.demo.module.trx;

import com.example.demo.module.trx.request.InquiryRequest;
import com.example.demo.module.trx.request.PaymentRequest;
import com.example.demo.module.trx.response.InquiryResponse;
import com.example.demo.module.trx.response.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trx")
public class TrxController {

    @Autowired
    TrxService service;

    @PostMapping("/inquiry")
    public InquiryResponse inquiry(@RequestBody InquiryRequest request) {
        return service.inquiry(request);
    }

    @PostMapping("/payment")
    public PaymentResponse inquiry(@RequestBody PaymentRequest request) {
        return service.payment(request);
    }
}
