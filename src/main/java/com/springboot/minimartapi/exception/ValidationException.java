package com.springboot.minimartapi.exception;

import com.springboot.minimartapi.baseerror.BasedError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class ValidationException {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    BasedError<?> handleValidation (MethodArgumentNotValidException e){
        List<?> errors = e.getFieldErrors().stream().map(fieldError ->
            Map.of(
                    "field error" , fieldError.getField(),
                    "error", Objects.requireNonNull(fieldError.getDefaultMessage()
                    ))
        ).toList();

        return BasedError.builder()
                .code(800)
                .error(errors)
                .message("Requested Data invalid")
                .timestamp(LocalDateTime.now())
                .build();
    }
}
