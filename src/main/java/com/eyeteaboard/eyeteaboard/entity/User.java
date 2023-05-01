package com.eyeteaboard.eyeteaboard.entity;

import com.eyeteaboard.eyeteaboard.dto.OAuthRegisterReqDto;
import com.eyeteaboard.eyeteaboard.type.Login;
import com.eyeteaboard.eyeteaboard.type.Role;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

  @Id
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String birth;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String detailAddress;

  @Column(nullable = false)
  private LocalDateTime regDt;

  @ColumnDefault("false")
  @Column(nullable = false)
  private boolean authYn;

  private LocalDateTime authDt;

  @Column(nullable = false)
  private String authKey;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  @Enumerated(EnumType.STRING)
  private Login loginType;

  // 엔티티에서 값 변경이 필요한 경우 목적과 의도를 나타낼 수 있는 메소드로 변경한다.
  public void permitAuth() {
    this.authDt = LocalDateTime.now();
    this.authYn = true;
  }

  public void updateInfo(String email, String name) {
    this.email = email;
    this.name = name;
  }

  public void registerOAuthGuest(OAuthRegisterReqDto dto) {
    this.password = UUID.randomUUID().toString();
    this.birth = dto.getBirth();
    this.address = dto.getAddress();
    this.detailAddress = dto.getDetailAddress();
    this.authYn = true;
    this.role = Role.USER;
  }
}
