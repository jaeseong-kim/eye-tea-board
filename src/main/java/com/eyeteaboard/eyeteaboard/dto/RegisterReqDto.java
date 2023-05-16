package com.eyeteaboard.eyeteaboard.dto;

import com.eyeteaboard.eyeteaboard.entity.User;
import com.eyeteaboard.eyeteaboard.type.Login;
import com.eyeteaboard.eyeteaboard.type.Role;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterReqDto {

  private String email;

  private String password;

  private String name;

  private String birth;

  private String address;

  private String detailAddress;

  private String uuid;

  public User toEntity() {
    return User.builder()
               .email(this.email)
               .password(this.password)
               .name(this.name)
               .birth(this.birth)
               .address(this.address)
               .detailAddress(this.detailAddress)
               .regDt(LocalDateTime.now())
               .authYn(false)
               .authKey(uuid)
               .status(true)
               .role(Role.USER)
               .loginType(Login.OUR_SERVICE)
               .build();
  }

  public void insertAuthKey(String uuid) {
    this.uuid = uuid;
  }

  public void encodePassword(String password) {
    this.password = password;
  }
}
