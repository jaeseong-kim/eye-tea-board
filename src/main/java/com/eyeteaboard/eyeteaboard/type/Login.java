package com.eyeteaboard.eyeteaboard.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Login {

  GOOGLE("구글"),
  NAVER("네이버");

  private final String value;
}
