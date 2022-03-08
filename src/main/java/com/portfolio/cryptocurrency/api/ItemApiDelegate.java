package com.portfolio.cryptocurrency.api;

import com.contract.cryptocurrency_portfolio_contract.api.ItemApiDelegate;
import com.contract.cryptocurrency_portfolio_contract.dto.CryptoCurrency;
import com.contract.cryptocurrency_portfolio_contract.dto.GlobalCryptoCurrency;
import com.portfolio.cryptocurrency.business.Wallet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
class ItemApiDelegateImpl implements ItemApiDelegate {

    Wallet wallet = new Wallet();

    @Override
    public ResponseEntity<GlobalCryptoCurrency> addCryptoCurrencyAsset(CryptoCurrency cryptoCurrency) {

        GlobalCryptoCurrency globalCryptoCurrency = wallet.addCryptoCurrencyAsset(cryptoCurrency);

        return new ResponseEntity<>(globalCryptoCurrency, HttpStatus.CREATED);
    }
}
