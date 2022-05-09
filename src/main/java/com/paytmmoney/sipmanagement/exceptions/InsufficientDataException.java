package com.paytmmoney.sipmanagement.exceptions;

public class InsufficientDataException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public InsufficientDataException(String message){
        super(message);
    }
}
