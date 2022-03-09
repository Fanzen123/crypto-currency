package com.portfolio.cryptocurrency.business;

import com.contract.cryptocurrency_portfolio_contract.dto.CryptoCurrency;
import com.contract.cryptocurrency_portfolio_contract.dto.GlobalCryptoCurrency;
import com.contract.cryptocurrency_portfolio_contract.dto.Symbol;

import java.util.HashMap;

public class Wallet {

    private HashMap<Symbol, GlobalCryptoCurrency> map = new HashMap<>();

    public GlobalCryptoCurrency addCryptoCurrencyAsset(CryptoCurrency cryptoCurrency) {

        GlobalCryptoCurrency globalCryptoCurrency;

        if(!map.containsValue(cryptoCurrency.getSymbol())) {
            globalCryptoCurrency = new GlobalCryptoCurrency();
            globalCryptoCurrency.setId(1L);
            globalCryptoCurrency.setSymbol(cryptoCurrency.getSymbol());
            globalCryptoCurrency.setAmount(0L);
            globalCryptoCurrency.setOldMarketValue(0L);
            globalCryptoCurrency.setActualMarketValue(0L);
            map.put(cryptoCurrency.getSymbol(), globalCryptoCurrency);
        } else {
            globalCryptoCurrency = map.get(cryptoCurrency.getSymbol());
        }


        globalCryptoCurrency.setAmount(globalCryptoCurrency.getAmount() + cryptoCurrency.getAmount());
        globalCryptoCurrency.setLocation(cryptoCurrency.getLocation());
        globalCryptoCurrency.setEntryDate(cryptoCurrency.getDate());
        globalCryptoCurrency.setActualMarketValue(cryptoCurrency.getAmount());
        globalCryptoCurrency.setOldMarketValue(cryptoCurrency.getAmount());

        return globalCryptoCurrency;
    }
}
