package com.portfolio.cryptocurrency.bo;

import com.contract.cryptocurrency_portfolio_contract.dto.GlobalCryptoCurrency;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class CryptoCurrencyDetails extends GlobalCryptoCurrency {

    @JsonIgnore
    private List<Asset> assets = new ArrayList();

    public List<Asset> getAssets() {
        return assets;
    }

}
