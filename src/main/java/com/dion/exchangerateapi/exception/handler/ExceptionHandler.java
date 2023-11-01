package com.dion.exchangerateapi.exception.handler;

import com.dion.exchangerateapi.exception.ApiNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({ApiNotFoundException.class})
    public final ResponseEntity handleApiNotFoundException(ApiNotFoundException ex) {
        System.out.println("error message:" + ex.getMessage());
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler({DuplicateKeyException.class})
    public final ResponseEntity handleDuplicateKeyException(DuplicateKeyException ex) {
        System.out.println("error message:" + ex.getMessage());
        return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
    }
}
