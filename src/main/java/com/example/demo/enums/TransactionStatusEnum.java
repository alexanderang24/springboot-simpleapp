package com.example.demo.enums;

import lombok.Getter;

@Getter
public enum TransactionStatusEnum {
    INQUIRED("INQUIRED", "Inquiry success"),
    SUCCESS("SUCCESS", "Payment success"),
    FAILED("FAILED", "Payment failed");

    private final String status;
    private final String message;

    TransactionStatusEnum(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
