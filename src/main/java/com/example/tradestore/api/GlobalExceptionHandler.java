package com.example.tradestore.api;

import com.example.tradestore.service.TradeValidationException;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TradeValidationException.class)
    public ResponseEntity<ApiError> handleValidation(TradeValidationException ex) {
        String cid = MDC.get("cid");
        ApiError err = new ApiError(400, "Bad Request", ex.getMessage(), cid);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        String cid = MDC.get("cid");
        ApiError err = new ApiError(500, "Internal Server Error", "An unexpected error occurred", cid);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }
}
