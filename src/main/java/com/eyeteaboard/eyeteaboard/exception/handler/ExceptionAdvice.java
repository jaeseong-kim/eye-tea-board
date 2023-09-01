package com.eyeteaboard.eyeteaboard.exception.handler;

import com.eyeteaboard.eyeteaboard.dto.ErrorResponse;
import com.eyeteaboard.eyeteaboard.exception.NoPostException;
import com.eyeteaboard.eyeteaboard.exception.NoUserException;
import com.eyeteaboard.eyeteaboard.type.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(NoUserException.class)
  public String handleNoUserException(Model model, NoUserException ex) {

    log.error(ex.toString());

    ErrorResponse errorResponse = new ErrorResponse(ex.getError());

    model.addAttribute("error", errorResponse);

    return "custom-error";
  }

  @ExceptionHandler(NoPostException.class)
  public String handleNoPostExcetion(Model model, NoPostException ex) {

    log.error(ex.toString());

    ErrorResponse errorResponse = new ErrorResponse(ex.getError());

    model.addAttribute("error", errorResponse);

    return "custom-error";
  }

  @ExceptionHandler(AccessDeniedException.class)
  public String handleAccessDeniedException(Model model, AccessDeniedException ex) {

    log.error(ex.toString());

    ErrorResponse errorResponse = new ErrorResponse(Error.FORBIDDEN);

    model.addAttribute("error", errorResponse);

    return "custom-error";
  }


  @ExceptionHandler(Exception.class)
  public String handleException(Model model, Exception ex) {

    log.error(ex.toString());

    ErrorResponse errorResponse = new ErrorResponse(Error.UNKNOW_ERROR);

    model.addAttribute("error", errorResponse);

    return "custom-error";
  }
}


