package com.eyeteaboard.eyeteaboard.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PasswordUpdateReqDto {

  @Email(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일 형식이 올바르지 않습니다.")
  private String email;

  @Size(min = 1, message = "비밀번호 최소 1자 이상 입력해야 합니다.")
  private String password;

  @Size(min = 1, message = "비밀번호 확인을 위해 한 번 더 입력해 주세요.")
  private String repassword;

  public boolean checkPassword() {
    return password.equals(repassword);
  }
}
