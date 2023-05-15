package com.eyeteaboard.eyeteaboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AdminFindUserDto {

  private String email;

  @Builder
  public AdminFindUserDto(String email){
    this.email = email;
  }
}
