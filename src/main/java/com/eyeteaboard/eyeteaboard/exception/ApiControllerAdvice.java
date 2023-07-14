package com.eyeteaboard.eyeteaboard.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleValidationException(
      MethodArgumentNotValidException ex) {
    BindingResult bindingResult = ex.getBindingResult();

    StringBuilder sb = new StringBuilder();
    for (FieldError error : bindingResult.getFieldErrors()) {
      sb.append(error.getField())
        .append(" : ")
        .append(error.getDefaultMessage())
        .append("\n");
    }

    return ResponseEntity.badRequest()
                         .body(sb.toString());
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<String> handleUserNameNotFoundException(UsernameNotFoundException ex) {

    StringBuilder sb = new StringBuilder();
    sb.append(ex.getMessage())
      .append("\n");

    return ResponseEntity.badRequest()
                         .body(sb.toString());
  }

}
