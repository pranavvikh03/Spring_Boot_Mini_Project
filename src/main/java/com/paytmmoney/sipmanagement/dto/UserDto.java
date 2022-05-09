package com.paytmmoney.sipmanagement.dto;

import com.paytmmoney.sipmanagement.model.UserDetails;
import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;


@Data
public class UserDto implements Serializable {

    private Long userId;
    @Valid
    private UserDetails userDetails;
}
