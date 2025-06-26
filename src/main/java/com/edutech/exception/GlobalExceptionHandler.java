package com.edutech.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("error", "Error interno");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityException(DataIntegrityViolationException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", HttpStatus.CONFLICT.value());
        error.put("error", "Conflicto de integridad de datos");
        error.put("message", "Ya existe un curso con los mismos datos.");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Error de Validacion");

        Map<String, String> errores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        response.put("message", errores);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}

