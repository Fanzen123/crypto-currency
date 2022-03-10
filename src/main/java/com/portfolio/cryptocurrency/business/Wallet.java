package com.portfolio.cryptocurrency.business;

import com.contract.cryptocurrency_portfolio_contract.dto.CryptoCurrency;
import com.contract.cryptocurrency_portfolio_contract.dto.GlobalCryptoCurrency;
import com.contract.cryptocurrency_portfolio_contract.dto.Symbol;

import java.io.IOException;
import java.util.HashMap;

public class Wallet {

    private final Market market = new Market();
    private HashMap<Symbol, GlobalCryptoCurrency> map = new HashMap<>();

    public GlobalCryptoCurrency addCryptoCurrencyAsset(CryptoCurrency cryptoCurrency) {

        GlobalCryptoCurrency globalCryptoCurrency;

        if(!map.containsKey(cryptoCurrency.getSymbol())) {
            globalCryptoCurrency = new GlobalCryptoCurrency();
            globalCryptoCurrency.setId(1L);
            globalCryptoCurrency.setSymbol(cryptoCurrency.getSymbol());
            globalCryptoCurrency.setAmount(cryptoCurrency.getAmount());
            globalCryptoCurrency.setEntryDate(cryptoCurrency.getDate());
            map.put(cryptoCurrency.getSymbol(), globalCryptoCurrency);
        } else {
            globalCryptoCurrency = map.get(cryptoCurrency.getSymbol());
            if(cryptoCurrency.getDate().isBefore(globalCryptoCurrency.getEntryDate())) {
                globalCryptoCurrency.setEntryDate(cryptoCurrency.getDate());
            }
            globalCryptoCurrency.setAmount(globalCryptoCurrency.getAmount() + cryptoCurrency.getAmount());
        }

        globalCryptoCurrency.setLocation(cryptoCurrency.getLocation());
        try {
            globalCryptoCurrency.setActualMarketValue(
                    market.getActualValue(globalCryptoCurrency.getSymbol()) * globalCryptoCurrency.getAmount());
        } catch (IOException e) {
            e.printStackTrace();
        }
        globalCryptoCurrency.setOldMarketValue(globalCryptoCurrency.getAmount() * 1);

        return globalCryptoCurrency;
    }
}
