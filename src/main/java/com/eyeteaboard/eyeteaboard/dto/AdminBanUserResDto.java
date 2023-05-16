package com.eyeteaboard.eyeteaboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminBanUserResDto {

  private boolean status;
  private String message;
}
