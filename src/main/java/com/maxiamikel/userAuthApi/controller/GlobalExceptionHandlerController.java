package com.maxiamikel.userAuthApi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.maxiamikel.userAuthApi.exception.BadRequestException;
import com.maxiamikel.userAuthApi.exception.ResourceAlreadyExistException;
import com.maxiamikel.userAuthApi.exception.ResourceNotFoundException;
import com.maxiamikel.userAuthApi.exception.StandardError;
import com.maxiamikel.userAuthApi.exception.UnauthorizedException;

@RestControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<StandardError> unauthorizedException(UnauthorizedException ex) {
        var message = StandardError
                .builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFoundException(ResourceNotFoundException ex) {
        var message = StandardError
                .builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        var message = StandardError
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Invalid ID format")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errors);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<StandardError> noResourceFoundException(NoResourceFoundException ex) {
        var message = StandardError
                .builder()
                .status(HttpStatus.BAD_GATEWAY.value())
                .message("URL not available")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(message);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex) {
        String error = "";
        if (ex.getCause().getMessage().contains("UKr43af9ap4edm43mmtq01oddj6")) {
            error = "Username is not available to use!";
        }
        var message = StandardError
                .builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .message(error)
                .build();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(message);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardError> badRequestException(BadRequestException ex) {
        var message = StandardError
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<StandardError> ResourceAlreadyExistException(ResourceAlreadyExistException ex) {
        var message = StandardError
                .builder()
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }
}
