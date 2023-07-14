package com.eyeteaboard.eyeteaboard.dto;

import com.eyeteaboard.eyeteaboard.entity.User;
import com.eyeteaboard.eyeteaboard.type.Login;
import com.eyeteaboard.eyeteaboard.type.Role;
import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

  @Email(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일 형식이 올바르지 않습니다.")
  private String email;

  @Size(min = 1, message = "비밀번호 최소 1자 이상 입력해야 합니다.")
  private String password;

  @Size(min = 2, message = "최소 2글자 입력해 주세요.")
  private String name;

  // ex) 1996-01-23
  @Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$", message = "생년월일 형식이 올바르지 않습니다.")
  private String birth;

  @NotBlank(message = "주소를 입력해 주세요.")
  private String address;

  @NotBlank(message = "세부 주소를 입력해 주세요.")
  private String detailAddress;

  // 서비스 단에서 uuid 삽입
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
