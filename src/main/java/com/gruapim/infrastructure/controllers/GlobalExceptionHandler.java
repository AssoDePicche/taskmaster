package com.gruapim.infrastructure.controllers;

import com.gruapim.application.dto.response.ErrorResponse;
import com.gruapim.application.services.exceptions.TaskNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handles(MethodArgumentNotValidException exception) {
    ErrorResponse response = new ErrorResponse(exception.getBindingResult()
                                                   .getFieldErrors()
                                                   .stream()
                                                   .map(FieldError::getDefaultMessage)
                                                   .toList()
                                                   .get(0));

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(TaskNotFoundException.class)
  public ResponseEntity<Void> handle(TaskNotFoundException exception) {
    return ResponseEntity.notFound().build();
  }
}
