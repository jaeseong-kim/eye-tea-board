package com.eyeteaboard.eyeteaboard.exception;

import com.eyeteaboard.eyeteaboard.type.Error;
import lombok.Getter;

@Getter
public class NoCommentException extends RuntimeException{

  private final Error error;

  public NoCommentException(Error error){
    this.error = error;
  }
}
