package com.paytmmoney.sipmanagement.controller;

import com.paytmmoney.sipmanagement.dto.PortfolioDto;
import com.paytmmoney.sipmanagement.services.PortfolioService;
import com.paytmmoney.sipmanagement.model.Portfolio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/Users/{userId}/Portfolio")
@Tag(name = "API for Portfolio", description = "API's for managing portfolio related operations")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "API for fetching user portfolio by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User portfolio fetched successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "No portfolio found for given user id", content=@Content)
    })
    @GetMapping
    public PortfolioDto fetchPortfolio(@Parameter(description = "User id for fetching portfolio") @PathVariable Long userId){
        return modelMapper.map(portfolioService.fetchPortfolio(userId), PortfolioDto.class);
    }

    @Operation(summary = "API for creating user portfolio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User portfolio created successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "User portfolio not created", content=@Content)
    })
    @PostMapping
    public Portfolio createPortfolio(@Valid @RequestBody Portfolio portfolio, @Parameter(description = "User id for creating user portfolio") @PathVariable Long userId){
        return portfolioService.createUserPortfolio(portfolio, userId);
    }

}
