package com.eyeteaboard.eyeteaboard.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PostLikeResDto {
  private boolean status;

  private int LikeNum;

  private String message;
}
