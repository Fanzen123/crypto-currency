package com.portfolio.cryptocurrency.api;

import com.contract.cryptocurrency_portfolio_contract.api.ItemApiDelegate;
import com.contract.cryptocurrency_portfolio_contract.dto.CryptoCurrency;
import com.contract.cryptocurrency_portfolio_contract.dto.GlobalCryptoCurrency;
import com.contract.cryptocurrency_portfolio_contract.dto.Symbol;
import com.portfolio.cryptocurrency.business.Wallet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
class ItemApiDelegateImpl implements ItemApiDelegate {

    Wallet wallet = new Wallet();

    @Override
    public ResponseEntity<GlobalCryptoCurrency> addCryptoCurrencyAsset(CryptoCurrency cryptoCurrency) {

        GlobalCryptoCurrency globalCryptoCurrency = null;
        try {
            globalCryptoCurrency = wallet.addCryptoCurrencyAsset(cryptoCurrency);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(globalCryptoCurrency, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<GlobalCryptoCurrency>> getCryptoCurrencies() {
        return new ResponseEntity<>(wallet.getCryptoCurrencies(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GlobalCryptoCurrency> getCryptoCurrency(Symbol symbol) {
        return new ResponseEntity<>(wallet.getCryptoCurrency(symbol), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteCryptoCurrency(Symbol symbol) {
        wallet.deleteCryptoCurrency(symbol);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
