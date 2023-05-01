package com.eyeteaboard.eyeteaboard.dto;

import com.eyeteaboard.eyeteaboard.entity.User;
import com.eyeteaboard.eyeteaboard.type.Login;
import com.eyeteaboard.eyeteaboard.type.Role;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OAuthAttributes {

  private Map<String, Object> attributes;
  private String nameAttributeKey;
  private String email;
  private String name;

  private Login loginType;

  public static OAuthAttributes of(String registrationId, String userNameAttributeName,
      Map<String, Object> attributes) {

    if ("naver".equals(registrationId)) {
      return ofNaver("id", attributes);
    }

    return ofGoogle(userNameAttributeName, attributes);
  }

  private static OAuthAttributes ofGoogle(String userNameAttributeName,
      Map<String, Object> attributes) {
    return OAuthAttributes.builder()
                          .name((String) attributes.get("name"))
                          .email((String) attributes.get("email"))
                          .attributes(attributes)
                          .nameAttributeKey(userNameAttributeName)
                          .loginType(Login.GOOGLE)
                          .build();
  }

  private static OAuthAttributes ofNaver(String userNameAttributeName,
      Map<String, Object> attributes) {
    Map<String, Object> response = (Map<String, Object>) attributes.get("response");

    return OAuthAttributes.builder()
                          .name((String)response.get("name"))
                          .email((String)response.get("email"))
                          .attributes(response)
                          .nameAttributeKey(userNameAttributeName)
                          .loginType(Login.NAVER)
                          .build();
  }

  public User toEntity() {
    return User.builder()
               .email(this.email)
               .password("")
               .name(this.name)
               .birth("")
               .address("")
               .detailAddress("")
               .regDt(LocalDateTime.now())
               .authKey("")
               .role(Role.GUSET)
               .loginType(loginType)
               .build();
  }
}
