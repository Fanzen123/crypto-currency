package com.portfolio.cryptocurrency.util;

import com.contract.cryptocurrency_portfolio_contract.dto.CryptoCurrency;
import com.contract.cryptocurrency_portfolio_contract.dto.FullCryptoCurrency;

/**
 * Mapper
 */
public class CryptoCurrencyMapper {

    /**
     * CryptoCurrency mapper
     * @param fullCryptoCurrency a fullCryptoCurrency
     * @return Mapped object
     */
    static public CryptoCurrency map(FullCryptoCurrency fullCryptoCurrency) {
        CryptoCurrency cryptoCurrency = new CryptoCurrency();
        cryptoCurrency.setId(fullCryptoCurrency.getId());
        cryptoCurrency.setAmount(fullCryptoCurrency.getAmount());
        cryptoCurrency.setSymbol(fullCryptoCurrency.getSymbol());
        cryptoCurrency.setEntryDate(fullCryptoCurrency.getEntryDate());
        cryptoCurrency.setOldMarketValue(fullCryptoCurrency.getOldMarketValue());
        cryptoCurrency.setActualMarketValue(fullCryptoCurrency.getActualMarketValue());
        cryptoCurrency.setLocation(fullCryptoCurrency.getLocation());
        return cryptoCurrency;
    }

}
