package com.dion.exchangerateapi.Controller;

import com.dion.exchangerateapi.models.dto.CurrencyCreateRequestDTO;
import com.dion.exchangerateapi.models.dto.CurrencyUpdateRequestDTO;
import com.dion.exchangerateapi.models.po.CurrencyPO;
import com.dion.exchangerateapi.service.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0/currency")
public class CurrencyController {

    final
    CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }


    @GetMapping("/{code}")
    public ResponseEntity<CurrencyPO> getCurrencyByCode(@PathVariable("code") String code) {

        Optional<CurrencyPO> currencyData = currencyService.getActiveCurrencyByCode(code);
        return currencyData.map(currencyPO -> new ResponseEntity<>(currencyPO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<CurrencyPO> createCurrency(@Valid @RequestBody CurrencyCreateRequestDTO currencyCreateRequestDTO) {

        CurrencyPO newCurrency = currencyService.createNewCurrency(currencyCreateRequestDTO);
        return new ResponseEntity<>(newCurrency, HttpStatus.CREATED);
    }

    @PutMapping("/{code}")
    public ResponseEntity<CurrencyPO> updateCurrency(@PathVariable("code") String code, @Valid @RequestBody CurrencyUpdateRequestDTO currencyUpdateRequestDTO) {

        CurrencyPO updateCurrency = currencyService.updateCurrency(code, currencyUpdateRequestDTO);
        return new ResponseEntity<>(updateCurrency, HttpStatus.OK);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<CurrencyPO> deleteCurrency(@PathVariable("code") String currencyCode) {

        CurrencyPO deleteCurrency = currencyService.softDeleteCurrency(currencyCode);
        return new ResponseEntity<>(deleteCurrency, HttpStatus.NO_CONTENT);
    }
}
