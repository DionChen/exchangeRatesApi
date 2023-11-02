package com.dion.exchangerateapi.service;

import com.dion.exchangerateapi.exception.ApiNotFoundException;
import com.dion.exchangerateapi.models.dto.CurrencyCreateRequestDTO;
import com.dion.exchangerateapi.models.dto.CurrencyUpdateRequestDTO;
import com.dion.exchangerateapi.models.po.CurrencyPO;
import com.dion.exchangerateapi.repository.CurrencyRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CurrencyService {


    final CurrencyRepository currencyRepositoryImpl;


    public CurrencyService(CurrencyRepository currencyRepositoryImpl) {
        this.currencyRepositoryImpl = currencyRepositoryImpl;
    }

    public Optional<CurrencyPO> getActiveCurrencyByCode(String code) {
        return currencyRepositoryImpl.findByCodeAndActive(code, true);
    }

    public CurrencyPO createNewCurrency(CurrencyCreateRequestDTO currencyCreateRequestDTO) {
        Optional<CurrencyPO> currency = currencyRepositoryImpl.findCurrencyPOByCode(currencyCreateRequestDTO.getCode());
        if (currency.isPresent()) {
            throw new DuplicateKeyException("Currency code is duplicate");
        } else {
            return currencyRepositoryImpl.save(
                    CurrencyPO.builder()
                            .code(currencyCreateRequestDTO.getCode())
                            .chName(currencyCreateRequestDTO.getChName())
                            .rate(currencyCreateRequestDTO.getRate())
                            .description(currencyCreateRequestDTO.getDescription())
                            .active(true)
                            .createdDate(new Date())
                            .lastModifiedDate(new Date())
                            .build()
            );
        }
    }

    public CurrencyPO updateCurrency(String currencyCode, CurrencyUpdateRequestDTO currencyUpdateRequestDTO) {
        Optional<CurrencyPO> currency = currencyRepositoryImpl.findCurrencyPOByCode(currencyCode);

        return currency.map(existingCurrency -> {
            Optional.ofNullable(currencyUpdateRequestDTO.getChName()).ifPresent(existingCurrency::setChName);
            Optional.ofNullable(currencyUpdateRequestDTO.getRate()).ifPresent(existingCurrency::setRate);
            Optional.ofNullable(currencyUpdateRequestDTO.getDescription()).ifPresent(existingCurrency::setDescription);
            existingCurrency.setLastModifiedDate(new Date());

            return currencyRepositoryImpl.save(existingCurrency);
        }).orElseThrow(() -> new ApiNotFoundException("Can't found currency: " + currencyCode));
    }

    public CurrencyPO softDeleteCurrency(String currencyCode) {
        Optional<CurrencyPO> currency = currencyRepositoryImpl.findCurrencyPOByCode(currencyCode);
        if (currency.isPresent()) {
            CurrencyPO deleteCurrency = currency.get();
            deleteCurrency.setActive(false);
            deleteCurrency.setLastModifiedDate(new Date());
            return currencyRepositoryImpl.save(deleteCurrency);
        } else {
            throw new ApiNotFoundException("Can't found currency: " + currencyCode);
        }
    }
}
