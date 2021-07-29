package com.example.demo.client.rajaongkir.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Rajaongkir {
    private Status status;
    private List<Result> results;
}
