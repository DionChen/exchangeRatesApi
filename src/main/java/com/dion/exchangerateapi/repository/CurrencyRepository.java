package com.dion.exchangerateapi.repository;

import com.dion.exchangerateapi.models.po.CurrencyPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<CurrencyPO, String> {

    Optional<CurrencyPO> findCurrencyPOByCode(String code);
    Optional<CurrencyPO> findByCodeAndActive(String code, Boolean active);


}
