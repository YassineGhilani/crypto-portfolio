package com.olky.portfolioservice;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final ExchangeRateServiceClient exchangeRateServiceClient;

    public PortfolioService(PortfolioRepository portfolioRepository, ExchangeRateServiceClient exchangeRateServiceClient) {
        this.portfolioRepository = portfolioRepository;
        this.exchangeRateServiceClient = exchangeRateServiceClient;
    }

    public Portfolio createPortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    public Optional<Portfolio> getPortfolio(Long id) {
        return portfolioRepository.findById(id);
    }

    public void deletePortfolio(Long id) {
        portfolioRepository.deleteById(id);
    }

    public Optional<Portfolio> addHolding(Long id, Holding holding) {
        return portfolioRepository.findById(id).map(portfolio -> {
            holding.setPortfolio(portfolio);
            portfolio.getHoldings().add(holding);
            return portfolioRepository.save(portfolio);
        });  
    }

    public boolean deleteHolding(Long id, String symbol) {
        return portfolioRepository.findById(id).map(portfolio -> {
            boolean removed = portfolio.getHoldings().removeIf(holding -> holding.getSymbol().equals(symbol));
            if (removed) {
                portfolioRepository.save(portfolio);
            }
            return removed;
        }).orElse(false);
    }

    public double getPortfolioValuation(Portfolio portfolio, String base) {
        double valuation = 0;
        for (Holding holding : portfolio.getHoldings()) {
            double exchangeRate = exchangeRateServiceClient.getExchangeRate(holding.getSymbol(), base);
            double holdingValue = holding.getQuantity() * exchangeRate;
            valuation += holdingValue;
        }
        return valuation;
    }
}
