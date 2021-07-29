package com.example.demo.client.rajaongkir.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Costs {
    private String service;
    private String description;
    private List<Cost> cost;
}
