package com.eyeteaboard.eyeteaboard.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentSaveReqDto {

  @NotNull
  private Long postId;
  @NotBlank(message = "내용을 입력해주세요.")
  private String comment;
}
