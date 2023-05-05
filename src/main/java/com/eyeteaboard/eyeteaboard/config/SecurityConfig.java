package com.eyeteaboard.eyeteaboard.config;

import com.eyeteaboard.eyeteaboard.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final CustomOAuth2UserService customOAuth2UserService;

  private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

  private final AuthenticationFailureHandler authenticationFailureHandler;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // 로그인, 로그아웃 설정
    http
        .csrf()
        .disable()
        .headers()
        .frameOptions()
        .disable()
        .and()
        .formLogin()
        .loginPage("/justlogin")
        .usernameParameter("email")
        .passwordParameter("password")
        .loginProcessingUrl("/loginProcess")
        .defaultSuccessUrl("/")
        .failureHandler(authenticationFailureHandler)
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/")
        .and()
        .oauth2Login()
        .successHandler(oAuth2LoginSuccessHandler)
        .userInfoEndpoint()
        .userService(customOAuth2UserService);

    // 접근 권한 설정
    http.authorizeRequests()
        .antMatchers("/", "/justlogin", "/user/**", "/post/list/**", "/h2-console/**")
        .permitAll()
        .anyRequest()
        .authenticated();
  }
}