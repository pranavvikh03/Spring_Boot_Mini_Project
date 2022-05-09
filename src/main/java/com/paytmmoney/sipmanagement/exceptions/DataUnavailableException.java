package com.paytmmoney.sipmanagement.exceptions;

public class DataUnavailableException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataUnavailableException(String message){
        super(message);
    }
}
