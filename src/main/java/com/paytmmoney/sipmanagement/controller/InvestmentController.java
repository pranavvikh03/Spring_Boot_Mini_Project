package com.paytmmoney.sipmanagement.controller;

import com.paytmmoney.sipmanagement.dto.InvestmentDto;
import com.paytmmoney.sipmanagement.services.InvestmentService;
import com.paytmmoney.sipmanagement.model.Investment;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping("/api/Users/{userId}/Portfolio/{portfolioId}/Investments")
@Tag(name = "API for Investment", description = "API's for managing investment operations")
public class InvestmentController {

    @Autowired
    private InvestmentService investmentService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "API for stopping existing investment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Investment stopped successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Problem in investment cancellation", content=@Content)
    })
    @DeleteMapping("/{investmentId}")
    public ResponseEntity<String> stopExistingInvestmentPlan(@PathVariable Long investmentId, @PathVariable Long userId, @PathVariable Long portfolioId){
        if(investmentService.stopExistingInvestment(investmentId, userId, portfolioId))
            return new ResponseEntity<>("Investment Stopped Successfully!!", HttpStatus.OK);
        else
            return new ResponseEntity<>("Problem in Stopping Investment", HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "API for updating details in existing investment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Details updated successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Problem in updation", content=@Content)
    })
    @PutMapping("/{investmentId}")
    public ResponseEntity<InvestmentDto> makeChangesInExistingInvestment(@Valid @RequestBody InvestmentDto updatedInvestmentDto, @PathVariable Long userId, @PathVariable Long portfolioId, @PathVariable Long investmentId){
        Investment investment = investmentService.updateExistingInvestment(modelMapper.map(updatedInvestmentDto,Investment.class),userId, portfolioId, investmentId);
        return new ResponseEntity<>(modelMapper.map(investment, InvestmentDto.class), HttpStatus.OK);
    }

    @Operation(summary = "API for starting new investment in existing portfolio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Investment Started Successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Problem in starting investment", content=@Content)
    })
    @PostMapping
    public ResponseEntity<InvestmentDto> addNewInvestmentToPortfolio(@Valid @RequestBody InvestmentDto newInvestmentDto, @PathVariable Long userId, @PathVariable Long portfolioId){
        Investment investment = investmentService.insertNewInvestmentToPortfolio(modelMapper.map(newInvestmentDto,Investment.class), userId, portfolioId);
        return new ResponseEntity<>(modelMapper.map(investment, InvestmentDto.class), HttpStatus.OK);
    }
}
