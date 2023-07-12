package com.eyeteaboard.eyeteaboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoUpdateReqDto {

  private String email;

  private String address;

  private String detailAddress;
}
