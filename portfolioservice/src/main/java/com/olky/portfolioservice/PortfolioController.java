package com.olky.portfolioservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/portfolios")
public class PortfolioController {

    @Autowired
    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping
    public ResponseEntity<Portfolio> savePortfolio(@RequestBody Portfolio portfolio) {
        Portfolio savedPortfolio = portfolioService.createPortfolio(portfolio);
        return ResponseEntity.ok(savedPortfolio);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> getPortfolio(@PathVariable Long id) {
        Optional<Portfolio> portfolio = portfolioService.getPortfolio(id);
        return portfolio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/holdings")
    public ResponseEntity<Portfolio> addPortfolioHoldings(@PathVariable Long id,@RequestBody Holding holding) {
        Optional<Portfolio> portfolio = portfolioService.addHolding(id,holding);
        return portfolio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/holdings/{symbol}")
    public ResponseEntity<Void> deleteHolding(@PathVariable Long id, @PathVariable String symbol) {
        boolean deleted = portfolioService.deleteHolding(id, symbol);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/valuation")
    public ResponseEntity<Double> getPortfolioValuation(@PathVariable Long id, @RequestParam String base) {
        Optional<Portfolio> portfolio = portfolioService.getPortfolio(id);
        if (portfolio.isPresent()) {
            double valuation = portfolioService.getPortfolioValuation(portfolio.get(), base);
            return ResponseEntity.ok(valuation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // @PostMapping("/{id}/holdings")
    // public ResponseEntity<Portfolio> addHoldingToPortfolio(@PathVariable Long id, @RequestBody Holding holding) {
    //     Optional<Portfolio> portfolio = portfolioService.getPortfolio(id);
    //     if (portfolio.isPresent()) {
    //         Portfolio updatedPortfolio = portfolioService.addHoldingToPortfolio(portfolio.get(), holding);
    //         return ResponseEntity.ok(updatedPortfolio);
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // @PostMapping("/{id}/holdings/{holdingId}")
    // public ResponseEntity<Portfolio> updateHoldingInPortfolio(@PathVariable Long id, @PathVariable Long holdingId, @RequestBody Holding holding) {
    //     Optional<Portfolio> portfolio = portfolioService.getPortfolio(id);
    //     if (portfolio.isPresent()) {
    //         Portfolio updatedPortfolio = portfolioService.updateHoldingInPortfolio(portfolio.get(), holdingId, holding);
    //         return ResponseEntity.ok(updatedPortfolio);
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

   


}
