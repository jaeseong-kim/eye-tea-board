package com.eyeteaboard.eyeteaboard.exception;

import com.eyeteaboard.eyeteaboard.dto.ErrorResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(NoUserException.class)
  public String handleNoUserException(Model model, NoUserException ex) {

    ErrorResponse errorResponse = new ErrorResponse(ex.getError());

    model.addAttribute("error", errorResponse);

    return "error";
  }

  @ExceptionHandler(NoPostException.class)
  public String handleNoPostExcetion(Model model, NoPostException ex) {

    ErrorResponse errorResponse = new ErrorResponse(ex.getError());

    model.addAttribute("error", errorResponse);

    return "error";
  }
}
