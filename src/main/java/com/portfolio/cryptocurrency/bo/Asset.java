package com.portfolio.cryptocurrency.bo;

public class Asset {

    private double number;
    private double valueAtTheTimeOfPurchase;

    public Asset(double number, double valueAtTheTimeOfPurchase) {
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
