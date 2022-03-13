package com.portfolio.cryptocurrency.business;

import com.contract.cryptocurrency_portfolio_contract.dto.*;
import com.portfolio.cryptocurrency.util.CryptoCurrencyMapper;

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
    private final HashMap<Symbol, FullCryptoCurrency> map = new HashMap<>();

    /**
     * Add a cryptoCurrency asset
     * @param cryptoCurrencyEntry a cryptoCurrencyEntry
     * @return a global state
     * @throws IOException an IOException
     */
    public CryptoCurrency addCryptoCurrencyAsset(CryptoCurrencyEntry cryptoCurrencyEntry) throws IOException {

        FullCryptoCurrency fullCryptoCurrency;

        double actualMarketValue = market.getActualValue(cryptoCurrencyEntry.getSymbol());

        if (!map.containsKey(cryptoCurrencyEntry.getSymbol())) {
            fullCryptoCurrency = new FullCryptoCurrency();
            fullCryptoCurrency.setId(map.size() + 1L);
            fullCryptoCurrency.setSymbol(cryptoCurrencyEntry.getSymbol());
            fullCryptoCurrency.setAmount(cryptoCurrencyEntry.getAmount());
            fullCryptoCurrency.setEntryDate(LocalDate.now());
            double globalActualMarketValue = actualMarketValue * fullCryptoCurrency.getAmount();
            fullCryptoCurrency.setActualMarketValue(globalActualMarketValue);
            map.put(cryptoCurrencyEntry.getSymbol(), fullCryptoCurrency);
        } else {
            fullCryptoCurrency = map.get(cryptoCurrencyEntry.getSymbol());
            fullCryptoCurrency.setAmount(fullCryptoCurrency.getAmount() + cryptoCurrencyEntry.getAmount());
            fullCryptoCurrency.setActualMarketValue(actualMarketValue * fullCryptoCurrency.getAmount());
        }

        Asset newAsset = new Asset();
        newAsset.setNumber(cryptoCurrencyEntry.getAmount());
        newAsset.setValueAtTheTimeOfPurchase(actualMarketValue);
        fullCryptoCurrency.getAssets().add(newAsset);

        Double valueAtTheTimeOfPurchase = fullCryptoCurrency.getAssets().stream()
                .map(asset -> asset.getNumber() * asset.getValueAtTheTimeOfPurchase())
                .reduce(0D, Double::sum);

        fullCryptoCurrency.setOldMarketValue(valueAtTheTimeOfPurchase);

        fullCryptoCurrency.setLocation(cryptoCurrencyEntry.getLocation());

        return CryptoCurrencyMapper.map(fullCryptoCurrency);
    }

    /**
     * GetCryptoCurrencies
     * @return a getCryptoCurrencies
     */
    public List<CryptoCurrency> getCryptoCurrencies() {
        return map.values().stream()
                .map(CryptoCurrencyMapper::map)
                .collect(Collectors.toList());
    }

    /**
     * GetCryptoCurrency
     * @param symbol a symbol
     * @return a cryptoCurrency
     */
    public CryptoCurrency getCryptoCurrency(Symbol symbol) {
        return map.values().stream()
                .map(CryptoCurrencyMapper::map)
                .filter(cryptoCurrencyBusiness -> cryptoCurrencyBusiness.getSymbol().equals(symbol))
                .findFirst().orElse(null);
    }

    /**
     * GetFullCryptoCurrency
     * @param symbol a symbol
     * @return a fullCryptoCurrency
     */
    public FullCryptoCurrency getFullCryptoCurrency(Symbol symbol) {
        return map.values().stream()
                .filter(cryptoCurrencyBusiness -> cryptoCurrencyBusiness.getSymbol().equals(symbol))
                .findFirst().orElse(null);
    }

    /**
     * DeleteCryptoCurrency
     * @param symbol a symbol
     */
    public void deleteCryptoCurrency(Symbol symbol) {
        map.remove(symbol);
    }

    /**
     * UpdateCryptoCurrency
     * @param fullCryptoCurrency a fullCryptoCurrency
     */
    public void updateCryptoCurrency(FullCryptoCurrency fullCryptoCurrency) {
        map.replace(fullCryptoCurrency.getSymbol(), fullCryptoCurrency);
    }
}
