package com.eyeteaboard.eyeteaboard.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoUpdateReqDto {

  @Email(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일 형식이 올바르지 않습니다.")
  private String email;

  @NotBlank(message = "주소를 입력해 주세요.")
  private String address;

  @NotBlank(message = "세부 주소를 입력해 주세요.")
  private String detailAddress;
}
