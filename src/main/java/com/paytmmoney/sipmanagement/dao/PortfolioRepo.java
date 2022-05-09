package com.paytmmoney.sipmanagement.dao;

import com.paytmmoney.sipmanagement.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepo extends JpaRepository<Portfolio,Long> {


    List<Portfolio> findByPortfolioIdAndUserUserId(Long portfolioId, Long userId);
    Optional<Portfolio> findByUserUserId(Long userId);
    boolean existsByUserUserId(Long userId);
    boolean existsByPortfolioIdAndUserUserId(Long portfolioId, Long userId);
}
