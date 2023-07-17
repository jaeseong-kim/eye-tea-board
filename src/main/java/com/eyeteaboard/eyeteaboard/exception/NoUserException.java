package com.eyeteaboard.eyeteaboard.exception;

import com.eyeteaboard.eyeteaboard.type.Error;
import lombok.Getter;

@Getter
public class NoUserException extends RuntimeException{

  private final Error error;

  public NoUserException(Error error){
    this.error = error;
  }

}
