package com.eyeteaboard.eyeteaboard.config;

import com.eyeteaboard.eyeteaboard.type.Error;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {

    Error error;
    if (exception instanceof UsernameNotFoundException) {
      //해당 계정이 없어도 강한 보안을 위해 BadCredentialsException으로 발생됨.
      error = Error.NO_USER;
    } else if (exception instanceof BadCredentialsException) {
      error = Error.WRONG_EMAIL_OR_PASSWORD;
    } else if(exception instanceof DisabledException){
      error = Error.NOT_PERMIT_AUTH_KEY;
    }else {
      error = Error.UNKNOW_ERROR;
    }

    setDefaultFailureUrl("/login?error=true&exception=" + URLEncoder.encode(error.getMessage(), "UTF-8"));
    super.onAuthenticationFailure(request, response, exception);
  }
}
