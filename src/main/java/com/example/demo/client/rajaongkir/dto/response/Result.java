package com.example.demo.client.rajaongkir.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Result {
    private String code;
    private String name;
    private List<Costs> costs;
}
