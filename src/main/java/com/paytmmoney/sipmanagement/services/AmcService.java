package com.paytmmoney.sipmanagement.services;

import com.paytmmoney.sipmanagement.dao.AmcRepo;
import com.paytmmoney.sipmanagement.exceptions.DataUnavailableException;
import com.paytmmoney.sipmanagement.exceptions.ResourceNotFoundException;
import com.paytmmoney.sipmanagement.model.Amc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AmcService {

    @Autowired
    private AmcRepo amcRepo;

    private Logger log = LoggerFactory.getLogger(AmcService.class);
    public AmcService(AmcRepo amcRepo) {
        this.amcRepo = amcRepo;
    }

    /*
            Function to fetch All Amcs
            if no amcs are in table then throws data unavailable exception
        */
    public List<Amc> fetchAllAmcs() {
        List<Amc> allAmcs = amcRepo.findAll();
        if(allAmcs.isEmpty())
            throw new DataUnavailableException("No Amc is Available / Amcs not Found");
        else
            return new ArrayList<>(allAmcs);
    }

    //Function to Create New Amc
    public Amc addNewAmc(Amc amc){
        Amc newAmc = null;
        amc.getSchemes().forEach(scheme -> scheme.setAmc(amc));
        newAmc = amcRepo.save(amc);
        return newAmc;
    }

    //Function to Fetch Amc Uniquely
    public Amc fetchAmcUniquely(String amcId) {
        return amcRepo.findById(amcId)
                .orElseThrow(()-> new ResourceNotFoundException("AMC of id: "+amcId+" Not Found"));
    }

    //Function to Delete Amc
    public boolean deleteAmc(String amcId) {
        if(!amcRepo.existsById(amcId)) throw new ResourceNotFoundException("AMC of id: "+amcId+" Not Found");
        else
        {
            boolean deletionStatus = false;
            try {
                amcRepo.deleteById(amcId);
                deletionStatus = true;
            } catch (Exception e) {
                log.error("Problem in Deletion");
            }
        return deletionStatus;
        }
    }
}
