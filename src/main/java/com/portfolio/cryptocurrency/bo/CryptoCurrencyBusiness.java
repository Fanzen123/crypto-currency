package com.portfolio.cryptocurrency.bo;

import com.contract.cryptocurrency_portfolio_contract.dto.GlobalCryptoCurrency;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class CryptoCurrencyBusiness extends GlobalCryptoCurrency {

    @JsonIgnore
    private List<AssetBusiness> assets = new ArrayList();

    public List<AssetBusiness> getAssets() {
        return assets;
    }

    public void setAssets(List<AssetBusiness> assets) {
        this.assets = assets;
    }
}
