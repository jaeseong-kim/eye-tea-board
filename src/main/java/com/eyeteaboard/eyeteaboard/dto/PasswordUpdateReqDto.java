package com.eyeteaboard.eyeteaboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PasswordUpdateReqDto {

  private String email;

  private String password;

  private String repassword;
}
