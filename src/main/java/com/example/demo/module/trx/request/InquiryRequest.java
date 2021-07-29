package com.example.demo.module.trx.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryRequest {
    private String inquiryCode;
    private String productName;
    private Integer amount;
}
