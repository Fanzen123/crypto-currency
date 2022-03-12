package com.portfolio.cryptocurrency.business;

import com.contract.cryptocurrency_portfolio_contract.dto.CryptoCurrency;
import com.contract.cryptocurrency_portfolio_contract.dto.GlobalCryptoCurrency;
import com.contract.cryptocurrency_portfolio_contract.dto.Symbol;
import com.portfolio.cryptocurrency.bo.AssetBusiness;
import com.portfolio.cryptocurrency.bo.CryptoCurrencyBusiness;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * Wallet
 */
public class Wallet {

    private final Market market = new Market();
    private final HashMap<Symbol, CryptoCurrencyBusiness> map = new HashMap<>();

    /**
     * Persist a cryptoCurrency asset
     * @param cryptoCurrency
     * @return a global state
     * @throws IOException an IOException
     */
    public GlobalCryptoCurrency addCryptoCurrencyAsset(CryptoCurrency cryptoCurrency) throws IOException {

        CryptoCurrencyBusiness cryptoCurrencyBusiness;

        double actualMarketValue = market.getActualValue(cryptoCurrency.getSymbol());

        if (!map.containsKey(cryptoCurrency.getSymbol())) {
            cryptoCurrencyBusiness = new CryptoCurrencyBusiness();
            cryptoCurrencyBusiness.setId(map.size() + 1L);
            cryptoCurrencyBusiness.setSymbol(cryptoCurrency.getSymbol());
            cryptoCurrencyBusiness.setAmount(cryptoCurrency.getAmount());
            cryptoCurrencyBusiness.setEntryDate(LocalDate.now());
            double globalActualMarketValue = actualMarketValue * cryptoCurrencyBusiness.getAmount();
            cryptoCurrencyBusiness.setActualMarketValue(globalActualMarketValue);
            map.put(cryptoCurrency.getSymbol(), cryptoCurrencyBusiness);
        } else {
            cryptoCurrencyBusiness = map.get(cryptoCurrency.getSymbol());
            cryptoCurrencyBusiness.setAmount(cryptoCurrencyBusiness.getAmount() + cryptoCurrency.getAmount());
            cryptoCurrencyBusiness.setActualMarketValue(actualMarketValue * cryptoCurrencyBusiness.getAmount());
        }

        cryptoCurrencyBusiness.getAssets().add(
                new AssetBusiness(cryptoCurrency.getAmount(), actualMarketValue));

        Double valueAtTheTimeOfPurchase = cryptoCurrencyBusiness.getAssets().stream()
                .map(asset -> asset.getNumber() * asset.getValueAtTheTimeOfPurchase())
                .reduce(0D, Double::sum);

        cryptoCurrencyBusiness.setOldMarketValue(valueAtTheTimeOfPurchase);

        cryptoCurrencyBusiness.setLocation(cryptoCurrency.getLocation());

        return cryptoCurrencyBusiness;
    }
}
