package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class SpringBootSimpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSimpleApplication.class, args);
    }

}
