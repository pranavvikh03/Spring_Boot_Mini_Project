package com.paytmmoney.sipmanagement.controller;

import com.paytmmoney.sipmanagement.dto.AmcDto;
import com.paytmmoney.sipmanagement.services.AmcService;
import com.paytmmoney.sipmanagement.exceptions.ResourceNotCreatedException;
import com.paytmmoney.sipmanagement.model.Amc;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "API for AMC", description = "API's for managing amc operations")
public class AmcController {

    @Autowired
    private AmcService amcService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "API for fetching all AMCS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All AMCS fetched Successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "No AMCS are Available in Table", content=@Content)
    })
    @GetMapping("/Amcs")
    public ResponseEntity<List<AmcDto>> fetchAllAmc(){
        List<AmcDto> amcDtos = amcService.fetchAllAmcs()
                .stream()
                .map(amc -> modelMapper.map(amc, AmcDto.class))
                .toList();
        return new ResponseEntity<>(amcDtos, HttpStatus.OK);
    }

    @Operation(summary = "API for creating or inserting new AMC")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AMC is created and inserted successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Problem in AMC Creation", content=@Content)
    })
    @PostMapping("/Amcs")
    public ResponseEntity<AmcDto> createNewAmc(@Valid @RequestBody AmcDto amcDto){
        Amc newAmc = amcService.addNewAmc(modelMapper.map(amcDto, Amc.class));
        if(newAmc == null)
            throw new ResourceNotCreatedException("Problem in AMC Creation");
        return new ResponseEntity<>(modelMapper.map(newAmc, AmcDto.class), HttpStatus.CREATED);
    }

    @Operation(summary = "API for fetching unique AMC by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AMC is available and fetched successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "AMC is not found with given id", content=@Content)
    })
    @GetMapping("/Amcs/{amcId}")
    public AmcDto fetchUniqueAmc(@Parameter(description = "AMC id for fetching AMC details") @PathVariable String amcId){
        return modelMapper.map(amcService.fetchAmcUniquely(amcId),AmcDto.class);
    }

    @Operation(summary = "API for Deleting existing AMC")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AMC Deleted Successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Problem in AMC Deletion", content=@Content)
    })
    @DeleteMapping("/Amcs/{amcId}")
    public ResponseEntity<String> deleteAmc(@Parameter(description = "AMC id for deletion of AMC") @PathVariable String amcId){
        if(amcService.deleteAmc(amcId))
            return new ResponseEntity<>("Asset Management System Deleted Successfully!!", HttpStatus.OK);
        else
            return new ResponseEntity<>("Problem in Deletion",HttpStatus.BAD_REQUEST);
    }
}
