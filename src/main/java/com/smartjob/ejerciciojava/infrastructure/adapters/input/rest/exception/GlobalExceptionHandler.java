package com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.exception;

import com.smartjob.ejerciciojava.domain.exception.InvalidEmailException;
import com.smartjob.ejerciciojava.domain.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
 @NotNull @jakarta.validation.constraints.Email
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({InvalidEmailException.class})
    public ResponseEntity<Object> handleInvalidEmail(InvalidEmailException exception) {
        Map<String, String> body = new HashMap<>();
        body.put("mensaje", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(body);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleMiscorrectIdFormatException(UserNotFoundException exception) {
        Map<String, String> body = new HashMap<>();
        body.put("mensaje", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body);
    }
}

