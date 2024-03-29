package com.eyeteaboard.eyeteaboard.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Error {
  NO_USER(HttpStatus.BAD_REQUEST,"존재하지 않는 이메일입니다. 회원가입 후 로그인하세요."),
  WRONG_EMAIL_OR_PASSWORD(HttpStatus.BAD_REQUEST,"잘못된 이메일 또는 비밀번호입니다."),
  NOT_PERMIT_AUTH_KEY(HttpStatus.UNAUTHORIZED,"이메일 미인증입니다. 인증 후 로그인하세요."),

  BANNED_USER(HttpStatus.BAD_REQUEST,"계정 정지된 유저입니다. 고객센터에 문의하세요."),
  NO_EXISTS_POST(HttpStatus.BAD_REQUEST,"존재하지 않는 게시글입니다."),
  NO_COMMENT(HttpStatus.BAD_REQUEST,"존재하지 않는 댓글입니다."),

  FORBIDDEN(HttpStatus.FORBIDDEN,"접근이 거부되었습니다."),
  UNKNOW_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"알 수 없는 에러입니다. 고객센터에 문의하세요.");

  private final HttpStatus status;
  private final String message;

}
