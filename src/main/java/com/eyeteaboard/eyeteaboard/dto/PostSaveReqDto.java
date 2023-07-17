package com.eyeteaboard.eyeteaboard.dto;

import com.eyeteaboard.eyeteaboard.entity.Post;
import com.eyeteaboard.eyeteaboard.entity.User;
import com.eyeteaboard.eyeteaboard.type.Category;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSaveReqDto {

  @NotBlank(message = "제목을 입력하세요.")
  private String title;

  @NotNull(message = "카테고리를 선택하세요.")
  private String category;

  @NotBlank(message = "내용을 입력하세요.")
  private String content;

  public Post toEntity(User email) {
    return Post.builder()
                .email(email)
                .title(title)
                .content(content)
                .likeNum(0)
                .category(Category.valueOf(category))
                .build();
  }
}
