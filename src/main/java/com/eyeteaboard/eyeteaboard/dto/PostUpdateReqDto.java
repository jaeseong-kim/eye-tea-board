package com.eyeteaboard.eyeteaboard.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostUpdateReqDto {
  @NotBlank(message = "제목을 입력하세요.")
  private String title;

  @NotNull(message = "카테고리를 선택하세요.")
  private String category;

  @NotBlank(message = "내용을 입력하세요.")
  private String content;

}
