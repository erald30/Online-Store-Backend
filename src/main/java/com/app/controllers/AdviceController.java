package com.app.controllers;

import com.app.dto.KeyValue;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;
@ControllerAdvice
public class AdviceController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        var result = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(o -> KeyValue.builder().key(o.getField()).value(o.getDefaultMessage()).build())
                .collect(Collectors.toList()) ;
        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
