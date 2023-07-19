package com.eyeteaboard.eyeteaboard.exception.handler;

import com.eyeteaboard.eyeteaboard.dto.ErrorResponse;
import com.eyeteaboard.eyeteaboard.exception.NoPostException;
import com.eyeteaboard.eyeteaboard.exception.NoUserException;
import com.eyeteaboard.eyeteaboard.type.Error;
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

  @ExceptionHandler(Exception.class)
  public String handleException(Model model, Exception ex) {

    ErrorResponse errorResponse = new ErrorResponse(Error.UNKNOW_ERROR);

    model.addAttribute("error", errorResponse);

    return "error";
  }
}
