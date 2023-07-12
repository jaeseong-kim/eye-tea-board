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
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {

  private final UserService userService;

  @PostMapping("/user/register")
  public RegisterResDto register(@RequestBody RegisterReqDto parameter) {
    return userService.register(parameter);
  }

  @PostMapping("/user/oauth-register")
  public OAuthRegisterResDto oauthRegister(@RequestBody OAuthRegisterReqDto parameter){
    return userService.oauthRegister(parameter);
  }

  @PutMapping("/user/update-password")
  public PasswordUpdateResDto updatePassword(@RequestBody PasswordUpdateReqDto parameter){
    return userService.updatePassword(parameter);
  }

  @PutMapping("/user/update-info")
  public UserInfoUpdateResDto updateProfile(@RequestBody UserInfoUpdateReqDto parameter){
    return userService.updateProfile(parameter);
  }
}
