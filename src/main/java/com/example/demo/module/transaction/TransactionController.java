package com.example.demo.module.transaction;

import com.example.demo.module.BaseResponse;
import com.example.demo.module.transaction.request.InquiryRequest;
import com.example.demo.module.transaction.request.PaymentRequest;
import com.example.demo.module.transaction.response.InquiryResponse;
import com.example.demo.module.transaction.response.PaymentResponse;
import com.example.demo.validator.CourierConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Validated
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService service;

    @PostMapping("/inquiry")
    public BaseResponse<InquiryResponse> inquiry(@RequestBody @Valid InquiryRequest request) {
        return service.inquiry(request);
    }

    @PostMapping("/payment")
    public BaseResponse<PaymentResponse> inquiry(@RequestBody @Valid PaymentRequest request) {
        return service.payment(request);
    }

    @GetMapping("/courier")
    public BaseResponse<BigDecimal> courierCost(
            @RequestParam("weight")
            @NotNull(message = "weight must not be null!")
            @Min(value = 1, message = "Minimum weight is 1 gram!")
            @Max(value = 30000, message = "Maximum weight is 30,000 gram!")
            Integer weight,

            @RequestParam("courier")
            @CourierConstraint
            String courier
    ) {
        return service.getCourierCost(courier, weight);
    }
}
