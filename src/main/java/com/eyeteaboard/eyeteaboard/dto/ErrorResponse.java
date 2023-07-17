package com.eyeteaboard.eyeteaboard.dto;

import com.eyeteaboard.eyeteaboard.type.Error;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

  private final HttpStatus status;
  private final int code;
  private final String msg;

  public ErrorResponse(Error error) {
    this.status = error.getStatus();
    this.code = error.getStatus().value();
    this.msg = error.getMessage();
  }

}
