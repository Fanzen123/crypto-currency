package com.portfolio.cryptocurrency.bo;

public class AssetBusiness {

    private double number;
    private double valueAtTheTimeOfPurchase;

    public AssetBusiness(double number, double valueAtTheTimeOfPurchase) {
        this.number = number;
        this.valueAtTheTimeOfPurchase = valueAtTheTimeOfPurchase;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public double getValueAtTheTimeOfPurchase() {
        return valueAtTheTimeOfPurchase;
    }

    public void setValueAtTheTimeOfPurchase(double valueAtTheTimeOfPurchase) {
        this.valueAtTheTimeOfPurchase = valueAtTheTimeOfPurchase;
    }
}
