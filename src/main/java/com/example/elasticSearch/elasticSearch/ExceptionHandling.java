package com.example.elasticSearch.elasticSearch;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex){
        ex.printStackTrace();
        return new  ResponseEntity<>("Exception occur " + ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
