package com.eyeteaboard.eyeteaboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentSaveReqDto {

  private Long postId;
  private String comment;
}
