package com.example.demo.module.transaction.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InquiryRequest {
    @NotNull(message = "product_id must be filled!")
    private Long productId;
    @NotNull(message = "quantity must be filled!")
    @Min(value = 1, message = "Minimum quantity is 1!")
    @Max(value = 99, message = "Maximum quantity is 99!")
    private Integer quantity;
}
