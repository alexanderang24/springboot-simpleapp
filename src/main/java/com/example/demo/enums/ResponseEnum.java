package com.example.demo.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {
    INQUIRED("INQUIRED", "Inquiry success"),
    SUCCESS("SUCCESS", "Payment success"),
    FAILED("FAILED", "Payment failed"),
    INVALID_PARAMETER("INVALID_PARAMETER", "Parameter provided is invalid"),
    UNKNOWN_ERROR("INTERNAL_SERVER_ERROR", "Internal server error"),
    TRANSACTION_NOT_FOUND("TRANSACTION_NOT_FOUND", "Transaction data not found"),
    PRODUCT_NOT_FOUND("PRODUCT_NOT_FOUND", "Product data not found"),
    TRANSACTION_ALREADY_PROCESSED("TRANSACTION_ALREADY_PROCESSED", "Transaction has been processed before"),
    CLIENT_ERROR("CLIENT_ERROR", "Failed when sending request to client"),
    ;

    private final String status;
    private final String message;

    ResponseEnum(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
