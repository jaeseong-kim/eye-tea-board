package com.eyeteaboard.eyeteaboard.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class OAuthRegisterReqDto {

  @Email(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일 형식이 올바르지 않습니다.")
  private String email;
  @Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$", message = "생년월일 형식이 올바르지 않습니다. 예) 2000-01-01")
  private String birth;
  @NotBlank(message = "주소를 입력해 주세요.")
  private String address;
  @NotBlank(message = "세부 주소를 입력해 주세요.")
  private String detailAddress;


}
