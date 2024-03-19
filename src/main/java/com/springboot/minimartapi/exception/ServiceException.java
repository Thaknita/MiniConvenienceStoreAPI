package com.springboot.minimartapi.exception;

import com.springboot.minimartapi.baseerror.BasedError;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ServiceException {

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<?> handleService(ResponseStatusException e){
        BasedError<?> basedError = BasedError.builder()
                .code(600)
                .timestamp(LocalDateTime.now())
                .message("Something went wrong")
                .error(e.getMessage())
                .build();

        return ResponseEntity.status(e.getStatusCode()).body(basedError);
    }





}
