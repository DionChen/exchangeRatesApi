package com.dion.exchangerateapi.models.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyUpdateRequestDTO {
    private String chName;

    private BigDecimal rate;

    private String description;

}
