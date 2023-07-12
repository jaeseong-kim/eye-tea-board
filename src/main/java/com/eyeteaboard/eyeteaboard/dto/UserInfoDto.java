package com.eyeteaboard.eyeteaboard.dto;

import com.eyeteaboard.eyeteaboard.entity.User;
import com.eyeteaboard.eyeteaboard.type.Login;
import com.eyeteaboard.eyeteaboard.type.Role;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class UserInfoDto {

  private String email;

  private String password;

  private String name;

  private String birth;
  private String address;
  private String detailAddress;
  private String regDt;
  private boolean authYn;

  private String authDt;

  private boolean status;

  private Role role;

  private Login loginType;

  public UserInfoDto(User entity) {
    this.email = entity.getEmail();
    this.password = entity.getPassword();
    this.name = entity.getName();
    this.birth = entity.getBirth();
    this.address = entity.getAddress();
    this.detailAddress = entity.getDetailAddress();
    this.regDt = entity.getRegDt().format(DateTimeFormatter.ISO_DATE);
    this.authYn = entity.isAuthYn();
    this.authDt = entity.getAuthDt().format(DateTimeFormatter.ISO_DATE);
    this.status = entity.isStatus();
    this.role = entity.getRole();
    this.loginType = entity.getLoginType();
  }
}
