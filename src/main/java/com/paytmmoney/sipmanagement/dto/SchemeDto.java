package com.paytmmoney.sipmanagement.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SchemeDto {

    private String schemeId;

    @NotEmpty(message = "Series should not be empty")
    private String series;

    @NotEmpty(message = "Scheme Name should be provided")
    private String schemeName;

    @NotEmpty(message = "ISIN No Should not be empty")
    private String isinNo;

    @NotEmpty(message = "RTA Scheme Code Field should not be empty")
    private String rtaSchemeCode;

}
