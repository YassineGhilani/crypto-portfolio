package com.olky.portfolioservice;

public class ExchangeRate {
    private String currency;
    private String base;
    private double rate;

    public ExchangeRate(String currency,String base, double rate) {
        this.currency = currency;
        this.base = base;
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }    

    public void setBase(String base) {
        this.base = base;
    }

    public String getBase() {
        return base;
    }

    public double getRate() {
        return rate;
    }
    public void setRate(double rate) {
        this.rate = rate;
    }

}
