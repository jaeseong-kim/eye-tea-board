package com.eyeteaboard.eyeteaboard.config;

import com.eyeteaboard.eyeteaboard.service.CustomOAuth2UserService;
import com.eyeteaboard.eyeteaboard.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomOAuth2UserService customOAuth2UserService;

  private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

  private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;

  private final CustomAuthFailureHandler authenticationFailureHandler;

  private final CustomAccessDeniedHandler accessDeniedHandler;



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
        .failureHandler(oAuth2LoginFailureHandler)
        .userInfoEndpoint()
        .userService(customOAuth2UserService);

    // 접근 권한 설정
    http.authorizeRequests()
        .mvcMatchers("/", "/justlogin", "/user/**", "/post/list/**", "/post/view/**",
            "/h2-console/**","/profile","/admin/**","error")
        .permitAll()
        .mvcMatchers("/post/save", "/post/update/**", "/post/like/**", "/post/delete/**",
            "/comment/**")
        .hasAnyAuthority(Role.USER.getKey(),Role.ADMIN.getKey());
  }
}
