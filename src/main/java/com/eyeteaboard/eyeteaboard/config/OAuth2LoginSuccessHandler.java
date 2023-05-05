package com.eyeteaboard.eyeteaboard.config;

import com.eyeteaboard.eyeteaboard.type.Role;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    //OAuth에서 로그인 성공 -> 추가 정보 입력 페이지로
    CustomOAuth2User user = (CustomOAuth2User) authentication.getPrincipal();


    if(user.getRole()== Role.GUSET){
      // 처음 간편 로그인한 사람이면 추가 정보 입력페이지로 이동
      response.sendRedirect("/user/oauth-register?email="+user.getEmail());

    }else{
      //이미 간편 로그인 회원가입 완료한 사람이면 메인 화면으로
      response.sendRedirect("/");
    }
  }
}
