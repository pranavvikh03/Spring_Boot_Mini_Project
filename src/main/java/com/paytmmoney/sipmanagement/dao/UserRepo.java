package com.paytmmoney.sipmanagement.dao;

import com.paytmmoney.sipmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    public List<User> findByUserDetailsEmail(String email);
    public boolean existsByUserDetailsEmail(String email);
}
