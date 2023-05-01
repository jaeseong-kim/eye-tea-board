package com.eyeteaboard.eyeteaboard.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
  GUSET("ROLE_GUEST","게스트"),
  USER("ROLE_USER","사용자"),
  ADMIN("ROLE_ADMIN","관리자");

  private final String key;
  private final String value;
}
