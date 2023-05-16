package com.eyeteaboard.eyeteaboard.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Login {

  GOOGLE("구글"),
  NAVER("네이버"),

  OUR_SERVICE("직접 가입");

  private final String value;
}
