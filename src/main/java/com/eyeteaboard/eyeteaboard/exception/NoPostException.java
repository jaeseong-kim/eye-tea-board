package com.eyeteaboard.eyeteaboard.exception;

import com.eyeteaboard.eyeteaboard.type.Error;
import lombok.Getter;

@Getter
public class NoPostException extends RuntimeException {

  private final Error error;

  public NoPostException(Error error) {
    this.error = error;
  }
}
