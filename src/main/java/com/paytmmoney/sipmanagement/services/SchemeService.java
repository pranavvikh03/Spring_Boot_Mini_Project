package com.paytmmoney.sipmanagement.services;

import com.paytmmoney.sipmanagement.dao.AmcRepo;
import com.paytmmoney.sipmanagement.dao.SchemeRepo;
import com.paytmmoney.sipmanagement.exceptions.ResourceNotFoundException;
import com.paytmmoney.sipmanagement.model.Scheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchemeService {

    @Autowired
    private SchemeRepo schemeRepo;

    @Autowired
    private AmcRepo amcRepo;

    private Logger log = LoggerFactory.getLogger(SchemeService.class);

    private static final String AMCNOTFOUNDERROR = "DB doesn't contain any AMC with given id:";
    /*
    *   It fetches all Schemes of particular amc using amc_id
    *   It Accepts amc_id as input and checks amc exists or not
    *   If yes:
    *       It return fetched schemes
    *   If no:
    *       throw new Resource Not found exception
    */
    public List<Scheme> fetchAllSchemes(String amcId) {
        if(!amcRepo.existsById(amcId))
            throw new ResourceNotFoundException(AMCNOTFOUNDERROR+""+amcId);
        else
            return new ArrayList<>(schemeRepo.findByAmcAmcId(amcId));
    }

    /*
    *   Fetched Particular Scheme using scheme id and amc id
    *   It Accepts schemeId and amcId as input and checks amc and scheme exists or not
     *   If yes:
     *        It return fetched scheme
     *   If no:
     *       throw new Resource Not found exception
    */
    public Scheme fetchUniqueScheme(String schemeId, String amcId) {
        if(!amcRepo.existsById(amcId))
            throw new ResourceNotFoundException(AMCNOTFOUNDERROR+""+amcId);
        else
            return schemeRepo.findBySchemeIdAndAmcAmcId(schemeId, amcId)
                .orElseThrow(() -> new ResourceNotFoundException("There is No Scheme with Id:"+schemeId));
    }

    /*
    *   It Creates new scheme
    *   It accepts scheme object and amcId as input
    *   and if amc exist then it saves scheme into DB
    */
    public Scheme createNewScheme(Scheme scheme, String amcId) {
        if(!amcRepo.existsById(amcId))
            throw new ResourceNotFoundException(AMCNOTFOUNDERROR+""+amcId);
        else
        {
            Scheme newScheme = null;
            scheme.setAmc(amcRepo.findById(amcId).orElseThrow(() -> new ResourceNotFoundException("No AMC available with id:" + amcId)));
            newScheme = schemeRepo.save(scheme);
            return newScheme;

        }
    }

    /*
    *   It delete scheme from table
    *   It takes input as scheme id and amc id and checks it exists or not
    *   If yes:
    *       delete scheme
    *   If no:
    *       throw exception resource not found exception
    */
    public boolean deleteScheme(String schemeId, String amcId) {
        boolean status = false;
        try{
            if(schemeRepo.existsBySchemeIdAndAmcAmcId(schemeId, amcId)) {
                schemeRepo.deleteById(schemeId);
                status = true;
            }
            else throw new ResourceNotFoundException("Scheme Not Found with id: " + schemeId+" in given amc id:"+amcId);
        }
        catch(Exception e){
            log.error("Problem in Deletion");
        }
        return status;

    }

    /*
    *   It updates existing scheme
    *   It takes scheme object and scheme id and amc id as input and checks for amc exist or not
    *   If yes:
    *       updates scheme
    *   if no:
    *       throw resource not found exception
    */
    public Scheme updateScheme(Scheme scheme, String schemeId, String amcId) {
        if(!amcRepo.existsById(amcId))
            throw new ResourceNotFoundException(AMCNOTFOUNDERROR+""+amcId);
        else
        {
            Scheme tempScheme = null;
            if (schemeRepo.existsBySchemeIdAndAmcAmcId(schemeId, amcId)) {
                scheme.setAmc(amcRepo.findById(amcId).orElseThrow(() -> new ResourceNotFoundException("Amc not found")));
                scheme.setSchemeId(schemeId);
                tempScheme = schemeRepo.save(scheme);
            } else
                throw new ResourceNotFoundException("Scheme Not Found with id: " + schemeId + " in given amc id:" + amcId);
            return tempScheme;
        }
    }
}
