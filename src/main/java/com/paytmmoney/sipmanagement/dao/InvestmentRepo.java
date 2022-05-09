package com.paytmmoney.sipmanagement.dao;

import com.paytmmoney.sipmanagement.model.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentRepo extends JpaRepository<Investment, Long> {
    boolean existsByFolioNoAndPortfolioPortfolioId(Long folioNo, Long portfolioId);
}