package com.paytmmoney.sipmanagement.dto;

import com.paytmmoney.sipmanagement.model.Portfolio;
import com.paytmmoney.sipmanagement.model.Scheme;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class InvestmentDto {

    private Long folioNo;

    @NotNull
    @Min(1)
    private Long shareCount;

    @DecimalMin(message = "Not Allowed", value = "1.0")
    private BigDecimal investAmount;

    @DecimalMin(message = "Not Allowed", value = "1.0")
    private BigDecimal currentAmount;
    private Date startDate;
    private Scheme scheme;
    private Portfolio portfolio;
}
