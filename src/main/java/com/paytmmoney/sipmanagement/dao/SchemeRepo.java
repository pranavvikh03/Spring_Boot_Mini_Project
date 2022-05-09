package com.paytmmoney.sipmanagement.dao;

import com.paytmmoney.sipmanagement.model.Scheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SchemeRepo extends JpaRepository<Scheme, String> {

    List<Scheme> findByAmcAmcId(String amcId);
    boolean existsBySchemeIdAndAmcAmcId(String id, String amcId);
    Optional<Scheme> findBySchemeIdAndAmcAmcId(String id, String amcId);
}
