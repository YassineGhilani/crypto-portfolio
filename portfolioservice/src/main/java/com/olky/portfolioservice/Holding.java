package com.olky.portfolioservice;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;

@Entity
public class Holding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;
    private double quantity;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    public Holding() {
    }

    public Holding(String symbol, double quantity) {
        this.symbol = symbol;
        this.quantity = quantity;
    }

    public Holding(String symbol, double quantity, Portfolio portfolio) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.portfolio = portfolio;
    }

    public Holding(Long id, String symbol, double quantity, Portfolio portfolio) {
        this.id = id;
        this.symbol = symbol;
        this.quantity = quantity;
        this.portfolio = portfolio;
    }

    public Holding(Long id, String symbol, double quantity) {
        this.id = id;
        this.symbol = symbol;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}
