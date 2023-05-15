package com.eyeteaboard.eyeteaboard.config;

import com.eyeteaboard.eyeteaboard.entity.User;
import com.eyeteaboard.eyeteaboard.type.Login;
import com.eyeteaboard.eyeteaboard.type.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

  private String email;
  private String password;

  private String name;

  private boolean authYn;

  private boolean status;
  private List<Role> roles;

  private Login loginType;

  public CustomUserDetails(User entity) {
    this.email = entity.getEmail();
    this.password = entity.getPassword();
    this.name = entity.getName();
    this.authYn = entity.isAuthYn();
    this.status=entity.isStatus();
    this.roles = new ArrayList<>();
    this.roles.add(entity.getRole());
    this.loginType = entity.getLoginType();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authorities = new ArrayList<>();

    authorities.add(new SimpleGrantedAuthority(roles.get(0)
                                                    .getKey()));

    if (email.equals("doctorwho123@naver.com")) {
      authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getKey()));
    }

    return authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    if(!status){
      return false;
    }

    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    if(!authYn){
      return false;
    }

    return true;
  }
}
