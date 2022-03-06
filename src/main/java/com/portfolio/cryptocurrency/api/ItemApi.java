package com.portfolio.cryptocurrency.api;

import com.contract.cryptocurrency_portfolio_contract.api.ItemApiDelegate;
import com.contract.cryptocurrency_portfolio_contract.dto.CryptoCurrency;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ItemApi implements ItemApiDelegate {

    @Override
    public ResponseEntity<CryptoCurrency> addCryptoCurrencyAsset(CryptoCurrency cryptoCurrency) {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }
}
