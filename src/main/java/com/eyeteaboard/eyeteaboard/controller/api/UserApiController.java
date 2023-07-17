package com.eyeteaboard.eyeteaboard.controller.api;

import com.eyeteaboard.eyeteaboard.dto.OAuthRegisterReqDto;
import com.eyeteaboard.eyeteaboard.dto.OAuthRegisterResDto;
import com.eyeteaboard.eyeteaboard.dto.PasswordUpdateResDto;
import com.eyeteaboard.eyeteaboard.dto.RegisterReqDto;
import com.eyeteaboard.eyeteaboard.dto.RegisterResDto;
import com.eyeteaboard.eyeteaboard.dto.PasswordUpdateReqDto;
import com.eyeteaboard.eyeteaboard.dto.UserInfoUpdateReqDto;
import com.eyeteaboard.eyeteaboard.dto.UserInfoUpdateResDto;
import com.eyeteaboard.eyeteaboard.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {

  private final UserService userService;

  /**
   * 회원가입 POST 요청 메소드입니다.
   * @param parameter 회원가입 정보 객체
   * @return RegisterResDto
   */
  @PostMapping("/user/register")
  public RegisterResDto register(@RequestBody @Valid RegisterReqDto parameter) {
    return userService.register(parameter);
  }

  /**
   * 간편 로그인 POST 요청 메소드입니다.
   * @param parameter 사용자 추가정보 객체
   * @return
   */
  @PostMapping("/user/oauth-register")
  public OAuthRegisterResDto oauthRegister(@RequestBody @Valid OAuthRegisterReqDto parameter){
    return userService.oauthRegister(parameter);
  }

  /**
   * 비밀번호 변경 PUT 요청 메소드입니다.
   * @param parameter 사용자 이메일, 비밀번호, 확인 비밀번호가 담긴 객체
   * @return
   */
  @PutMapping("/user/update-password")
  public PasswordUpdateResDto updatePassword(@RequestBody @Valid PasswordUpdateReqDto parameter){
    return userService.updatePassword(parameter);
  }

  /**
   * 개인정보 수정 PUT 요청 메소드입니다.
   * @param parameter 사용자 개인정보 객체
   * @return
   */
  @PutMapping("/user/update-info")
  public UserInfoUpdateResDto updateProfile(@RequestBody @Valid UserInfoUpdateReqDto parameter){
    return userService.updateProfile(parameter);
  }
}
