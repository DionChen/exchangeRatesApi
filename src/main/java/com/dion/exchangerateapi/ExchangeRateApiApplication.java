package com.dion.exchangerateapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ExchangeRateApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeRateApiApplication.class, args);
    }

}
