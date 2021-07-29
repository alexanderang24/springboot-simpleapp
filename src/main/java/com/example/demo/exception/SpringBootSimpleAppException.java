package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SpringBootSimpleAppException extends RuntimeException {

    private final String code;
    private final HttpStatus httpStatus;

    public SpringBootSimpleAppException(String message, String code, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
