package com.paytmmoney.sipmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errorInfo = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorInfo.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request){
        ErrorInfo errorInfo = new ErrorInfo(new Date(), exception.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientDataException.class)
    public ResponseEntity<ErrorInfo> handleResourceNotCreatedException(InsufficientDataException exception, WebRequest request){
        ErrorInfo errorInfo = new ErrorInfo(new Date(), exception.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotCreatedException.class)
    public ResponseEntity<ErrorInfo> handleResourceNotCreatedException(ResourceNotCreatedException exception, WebRequest request){
        ErrorInfo errorInfo = new ErrorInfo(new Date(), exception.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> handleGlobalException(Exception exception, WebRequest request){
        ErrorInfo errorInfo = new ErrorInfo(new Date(), exception.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

}
