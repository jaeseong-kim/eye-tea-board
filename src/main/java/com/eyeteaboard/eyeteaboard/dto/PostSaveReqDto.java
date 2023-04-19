package com.eyeteaboard.eyeteaboard.dto;

import com.eyeteaboard.eyeteaboard.entity.Post;
import com.eyeteaboard.eyeteaboard.entity.User;
import com.eyeteaboard.eyeteaboard.type.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSaveReqDto {

  private String title;

  private String category;

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
