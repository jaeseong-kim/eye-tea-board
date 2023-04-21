package com.eyeteaboard.eyeteaboard.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
  KNOWLEDGE("지식"),
  DAILY_LIFE("사는 이야기"),
  STUDY("스터디");

  private final String value;

}
