package com.fetchrewards.receiptprocessor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

/**
 * Global Exception Handler for the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle validation errors (e.g., missing fields or incorrect formats).
     *
     * @param ex the MethodArgumentNotValidException
     * @return a ResponseEntity with a 400 status and error message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "The receipt is invalid."));
    }

    /**
     * Handle NoSuchReceiptException and return a 404 Not Found status.
     *
     * @param ex the NoSuchReceiptException
     * @return a ResponseEntity with a 404 status and error message
     */
    @ExceptionHandler(NoSuchReceiptException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleNoSuchReceiptException(NoSuchReceiptException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "No receipt found for that ID."));
    }

    /**
     * Handle unexpected server errors.
     *
     * @param ex the Exception
     * @return a ResponseEntity with a 500 status and error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "An unexpected error occurred. Please try again later."));
    }
}