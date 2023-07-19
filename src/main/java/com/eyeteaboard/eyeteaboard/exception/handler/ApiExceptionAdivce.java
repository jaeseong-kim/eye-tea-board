package com.eyeteaboard.eyeteaboard.exception.handler;

import com.eyeteaboard.eyeteaboard.dto.ErrorResponse;
import com.eyeteaboard.eyeteaboard.exception.NoCommentException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdivce {

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

  @ExceptionHandler(NoCommentException.class)
  public ResponseEntity<ErrorResponse> handleNoCommentException(NoCommentException ex){

    ErrorResponse errorResponse = new ErrorResponse(ex.getError());

    return ResponseEntity.badRequest().body(errorResponse);
  }



}
