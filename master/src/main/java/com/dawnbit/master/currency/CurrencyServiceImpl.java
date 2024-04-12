package com.dawnbit.master.currency;

import com.dawnbit.entity.master.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public List<Currency> fetchCurrency() {
        return this.currencyRepository.findAll();
    }
}
