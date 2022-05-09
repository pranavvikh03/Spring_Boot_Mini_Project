package com.paytmmoney.sipmanagement.services;

import com.paytmmoney.sipmanagement.dao.PortfolioRepo;
import com.paytmmoney.sipmanagement.dao.SchemeRepo;
import com.paytmmoney.sipmanagement.dao.UserRepo;
import com.paytmmoney.sipmanagement.exceptions.ResourceNotCreatedException;
import com.paytmmoney.sipmanagement.exceptions.ResourceNotFoundException;
import com.paytmmoney.sipmanagement.model.Portfolio;
import com.paytmmoney.sipmanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepo portfolioRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SchemeRepo schemeRepo;

    /*
    *   Function for fetching user portfolio
    *   It takes userid as input and checks user has created portfolio or not
    *   if yes then return user portfolio
    *   else throws exception
    */
    public Portfolio fetchPortfolio(Long userId) {
        if(!userRepo.existsById(userId))
            throw new ResourceNotFoundException("User with id:"+userId+" not exist");
        else
            return portfolioRepo.findByUserUserId(userId)
                    .orElseThrow(()-> new ResourceNotFoundException("User id:"+userId+" haven't created any profile yet"));
    }

    /*
    *   Function for creating new user portfolio
    *   It takes portfolio object and userid
    *   firstly it checks user exists or not
    *   if exists then creates portfolio
    *   else throws exception
    */
    public Portfolio createUserPortfolio(Portfolio portfolio, Long userId) {
        if(!userRepo.existsById(userId))
            throw new ResourceNotFoundException("User with id:"+userId+" not exist");
        else
        {
            Portfolio portfolioTemp = null;
            if (!portfolioRepo.existsByUserUserId(userId)) {
                User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
                portfolio.setUser(user);
                portfolio.getInvestments().forEach(investment -> {
                    investment.setStartDate(new Date());
                    investment.setScheme(schemeRepo.findById(investment.getScheme().getSchemeId())
                            .orElseThrow(()-> new ResourceNotFoundException("No scheme exist with given id")));
                });
                portfolioTemp = portfolioRepo.save(portfolio);
            }
            else
                throw new ResourceNotCreatedException("User portfolio already exist");
            return portfolioTemp;
        }
    }
}
