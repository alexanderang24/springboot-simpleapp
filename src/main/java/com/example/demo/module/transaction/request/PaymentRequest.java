package com.example.demo.module.transaction.request;

import com.example.demo.validator.CourierConstraint;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PaymentRequest {
    @NotBlank(message = "inquiry_code must be filled!")
    private String inquiryCode;
    @CourierConstraint
    private String courier;
}
