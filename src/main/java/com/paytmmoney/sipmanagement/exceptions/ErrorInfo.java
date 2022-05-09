package com.paytmmoney.sipmanagement.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInfo {

    private String message;
    private Date timeStamp;
    private String description;

    public ErrorInfo(Date date, String message, String description) {
        super();
        this.message = message;
        this.timeStamp = date;
        this.description = description;
    }
}
