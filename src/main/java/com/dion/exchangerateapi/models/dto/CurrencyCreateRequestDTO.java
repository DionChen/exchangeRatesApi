package com.dion.exchangerateapi.models.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
public class CurrencyCreateRequestDTO {

    @NotBlank
    @Pattern(regexp = "[A-Z]{3}", message = "Invalid currency code. It should be three uppercase letters.")
    private String code;

    private String chName;

    private BigDecimal rate;

    private String description;

}
