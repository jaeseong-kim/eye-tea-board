package com.eyeteaboard.eyeteaboard.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
  KNOWLEDGE("KNOWLEDGE"),
  DAILY_LIFE("DAILY_LIFE"),
  STUDY("STUDY");

  private final String value;

}
