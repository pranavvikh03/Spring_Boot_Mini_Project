package com.paytmmoney.sipmanagement.services;

import com.paytmmoney.sipmanagement.dao.InvestmentRepo;
import com.paytmmoney.sipmanagement.dao.PortfolioRepo;
import com.paytmmoney.sipmanagement.dao.SchemeRepo;
import com.paytmmoney.sipmanagement.dao.UserRepo;
import com.paytmmoney.sipmanagement.exceptions.ResourceNotFoundException;
import com.paytmmoney.sipmanagement.model.Investment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InvestmentService {

    @Autowired
    private InvestmentRepo investmentRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PortfolioRepo portfolioRepo;

    @Autowired
    private SchemeRepo schemeRepo;

    private Logger log = LoggerFactory.getLogger(InvestmentService.class);

    public List<Investment> fetchAllInvestments(){
        List<Investment> investments = new ArrayList<>();
        investmentRepo.findAll().forEach(investments::add);
        return investments;
    }

    /*
    *   Function for stoping or deleting existing investment
    *   It takes investment id , userid , portfolioid as input and checks for availability
    *   if all parameters are correct it deletes investment record
    *   if anyone is incorrect then throws exception.
    */
    public boolean stopExistingInvestment(Long investmentId, Long userId, Long portfolioId) {
        if(portfolioRepo.existsByPortfolioIdAndUserUserId(portfolioId, userId) && investmentRepo.existsByFolioNoAndPortfolioPortfolioId(investmentId, portfolioId)){
            boolean status = false;
            try {
                investmentRepo.deleteById(investmentId);
                status = true;
            } catch (Exception e) {
                log.error("Problem in deletion");
            }
            return status;
        }
        else
            throw new ResourceNotFoundException("Invalid Data");
    }

    /*
    *   Function to update existing investment details
    *   It takes all required parameter with investment object of updated details
    *   if all parameters are correct it updates
    *   if anyone is incorrect then throws exception
    */
    public Investment updateExistingInvestment(Investment updatedInvestment, Long userId, Long portfolioId, Long investmentId) {
        if(portfolioRepo.existsByPortfolioIdAndUserUserId(portfolioId, userId) && investmentRepo.existsByFolioNoAndPortfolioPortfolioId(investmentId, portfolioId)){
            updatedInvestment.setPortfolio(portfolioRepo.findById(portfolioId).orElseThrow(() -> new ResourceNotFoundException("Portfolio dosen't exist")));
            updatedInvestment.setFolioNo(investmentId);
            updatedInvestment.setScheme(schemeRepo.findById(updatedInvestment.getScheme().getSchemeId())
                    .orElseThrow(()-> new ResourceNotFoundException("No scheme exist with given id")));
            Investment investment = null;
            investment = investmentRepo.save(updatedInvestment);
            return investment;
        }
        else
            throw new ResourceNotFoundException("Invalid Data");
    }

    /*
    *   Function for starting new investment in existing portfolio
    *   It takes all required parameter and new investment object and checks given parameter is correct or not
    *   If everything is correct then save object to db
    *   else throws exception
    */
    public Investment insertNewInvestmentToPortfolio(Investment newInvestment, Long userId, Long portfolioId) {
        if(portfolioRepo.existsByPortfolioIdAndUserUserId(portfolioId, userId)){
            newInvestment.setPortfolio(portfolioRepo.findById(portfolioId).orElseThrow(() -> new ResourceNotFoundException("Portfolio dosen't exist")));
            newInvestment.setStartDate(new Date());
            newInvestment.setScheme(schemeRepo.findById(newInvestment.getScheme().getSchemeId())
                    .orElseThrow(()-> new ResourceNotFoundException("No scheme exist with given id")));
            Investment investment = null;
            investment = investmentRepo.save(newInvestment);
            return investment;

        }
        else
            throw new ResourceNotFoundException("No portfolio exist with portfolio id:"+portfolioId+" and user id:"+userId);
    }
}
