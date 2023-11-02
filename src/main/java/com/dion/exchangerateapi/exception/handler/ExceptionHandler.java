package com.dion.exchangerateapi.exception.handler;

import com.dion.exchangerateapi.exception.ApiNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

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

    @org.springframework.web.bind.annotation.ExceptionHandler({HttpStatusCodeException.class})
    public final ResponseEntity handleHttpStatusCodeException(HttpStatusCodeException ex) {
        HttpStatus statusCode = ex.getStatusCode();
        String responseBody = ex.getResponseBodyAsString();
        if (statusCode == HttpStatus.NOT_FOUND) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("API returned 404: " + responseBody);
        } else if (statusCode == HttpStatus.BAD_REQUEST) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("API returned 400: " + responseBody);
        } else {
            return ResponseEntity.status(statusCode).body("API error: " + responseBody);
        }

    }

}
