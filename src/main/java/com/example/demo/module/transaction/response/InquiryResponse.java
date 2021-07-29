package com.example.demo.module.transaction.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryResponse {
    private String inquiryCode;
    private String status;
    private String message;
}
