package com.paytmmoney.sipmanagement.dto;

import com.paytmmoney.sipmanagement.model.Investment;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class PortfolioDto {

    private Long portfolioId;

    private List<@Valid Investment> investments;

}
