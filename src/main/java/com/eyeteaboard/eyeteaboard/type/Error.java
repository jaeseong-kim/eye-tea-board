package com.eyeteaboard.eyeteaboard.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Error {
  NO_USER("존재하지 않는 이메일입니다. 회원가입 후 로그인하세요."),
  WRONG_EMAIL_OR_PASSWORD("잘못된 이메일 또는 비밀번호입니다."),
  NOT_PERMIT_AUTH_KEY("이메일 미인증입니다. 인증 후 로그인하세요."),

  BANNED_USER("계정 정지된 유저입니다. 고객센터에 문의하세요."),
  UNKNOW_ERROR("알 수 없는 에러입니다. 고객센터에 문의하세요.");
  private final String message;

}
