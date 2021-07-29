package com.example.demo.client.rajaongkir.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CostRequest {
    private String origin;
    private String destination;
    private String weight;
    private String courier;
}
