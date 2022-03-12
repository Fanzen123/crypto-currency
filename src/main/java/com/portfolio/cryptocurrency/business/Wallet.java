package com.portfolio.cryptocurrency.business;

import com.contract.cryptocurrency_portfolio_contract.dto.CryptoCurrency;
import com.contract.cryptocurrency_portfolio_contract.dto.GlobalCryptoCurrency;
import com.contract.cryptocurrency_portfolio_contract.dto.Symbol;
import com.portfolio.cryptocurrency.bo.Asset;
import com.portfolio.cryptocurrency.bo.CryptoCurrencyDetails;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Wallet
 */
public class Wallet {

    private final Market market = new Market();
    private final HashMap<Symbol, CryptoCurrencyDetails> map = new HashMap<>();

    /**
     * Persist a cryptoCurrency asset
     * @param cryptoCurrency
     * @return a global state
     * @throws IOException an IOException
     */
    public GlobalCryptoCurrency addCryptoCurrencyAsset(CryptoCurrency cryptoCurrency) throws IOException {

        CryptoCurrencyDetails cryptoCurrencyBusiness;

        double actualMarketValue = market.getActualValue(cryptoCurrency.getSymbol());

        if (!map.containsKey(cryptoCurrency.getSymbol())) {
            cryptoCurrencyBusiness = new CryptoCurrencyDetails();
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
                new Asset(cryptoCurrency.getAmount(), actualMarketValue));

        Double valueAtTheTimeOfPurchase = cryptoCurrencyBusiness.getAssets().stream()
                .map(asset -> asset.getNumber() * asset.getValueAtTheTimeOfPurchase())
                .reduce(0D, Double::sum);

        cryptoCurrencyBusiness.setOldMarketValue(valueAtTheTimeOfPurchase);

        cryptoCurrencyBusiness.setLocation(cryptoCurrency.getLocation());

        return cryptoCurrencyBusiness;
    }

    public List<GlobalCryptoCurrency> getCryptoCurrencies() {
        return map.values().stream().collect(Collectors.toList());
    }

    public GlobalCryptoCurrency getCryptoCurrency(Symbol symbol) {
        return map.values().stream()
                .filter(cryptoCurrencyBusiness -> cryptoCurrencyBusiness.getSymbol().equals(symbol))
                .findFirst().orElse(null);
    }

    public void deleteCryptoCurrency(Symbol symbol) {
        map.remove(symbol);
    }
}
