package com.eyeteaboard.eyeteaboard.dto;

import com.eyeteaboard.eyeteaboard.entity.User;
import lombok.Getter;

@Getter
public class AdminFindUserDto {

  private String email;

  public AdminFindUserDto(User user) {
    this.email = user.getEmail();
  }

}
