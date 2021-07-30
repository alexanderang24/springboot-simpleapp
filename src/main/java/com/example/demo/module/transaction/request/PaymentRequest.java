package com.example.demo.module.transaction.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PaymentRequest {
    @NotBlank(message = "inquiry_code must be filled!")
    private String inquiryCode;
    @NotNull(message = "courier_cost must not be null!")
    @Min(value = 1, message = "courier_cost exceed minimum threshold!")
    private BigDecimal courierCost;
}
