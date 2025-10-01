package com.gruapim.adapters.controllers;

import com.gruapim.adapters.controllers.exceptions.NotFoundException;
import com.gruapim.application.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handle(Exception exception) {
    ErrorResponse response = new ErrorResponse(exception.getMessage());

    return ResponseEntity.internalServerError().body(response);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handle(IllegalArgumentException exception) {
    ErrorResponse response = new ErrorResponse(exception.getMessage());

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Void> handle(NotFoundException exception) {
    return ResponseEntity.notFound().build();
  }
}
