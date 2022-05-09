package com.paytmmoney.sipmanagement.dto;

import com.paytmmoney.sipmanagement.model.Scheme;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class AmcDto {

    @NotEmpty(message = "AMC Id is Required")
    private String amcId;

    @NotEmpty(message = "Amc Name should not be Empty")
    @Size(min = 4 , max = 500)
    private String name;

    private List<@Valid Scheme> schemes;
}
