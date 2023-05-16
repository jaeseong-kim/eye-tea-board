package com.eyeteaboard.eyeteaboard.service;

import com.eyeteaboard.eyeteaboard.config.CustomOAuth2User;
import com.eyeteaboard.eyeteaboard.dto.OAuthAttributes;
import com.eyeteaboard.eyeteaboard.entity.User;
import com.eyeteaboard.eyeteaboard.repository.UserRepository;
import com.eyeteaboard.eyeteaboard.type.Error;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final UserRepository userRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration()
                                       .getRegistrationId(); //구글인지 네이버인지 확인하는 아이디
    String userNameAttributeName = userRequest.getClientRegistration()
                                              .getProviderDetails()
                                              .getUserInfoEndpoint()
                                              .getUserNameAttributeName();

    //oauth2로 가져온 정보 확인하기
    log.info(oAuth2User.getAttributes().toString());

    OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
        oAuth2User.getAttributes());

    // userRepo에 저장
    User user = saveOrUpdate(attributes);

    if (user.isBan()) {
      throw new LockedException(Error.BANNED_USER.getMessage());
    }

    return new CustomOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority(user.getRole().getKey())),
        attributes.getAttributes(), attributes.getNameAttributeKey(), user.getEmail(),
        user.getRole());
  }

  private User saveOrUpdate(OAuthAttributes attributes) {
    Optional<User> optionalUser = userRepository.findByEmail(attributes.getEmail());

    User user;
    if (optionalUser.isPresent()) {
      user = optionalUser.get();
      user.updateInfo(attributes.getEmail(), attributes.getName());
    } else {
      user = attributes.toEntity();
    }

    return userRepository.save(user);
  }
}
