package com.eyeteaboard.eyeteaboard.dto;


import com.eyeteaboard.eyeteaboard.entity.Post;
import com.eyeteaboard.eyeteaboard.type.Category;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class PostListResDto {

  public Long id;

  private String email;

  private String title;

  private int likeNum;

  private Category category;

  private String regDt;

  public PostListResDto(Post entity) {
    this.id = entity.getPostId();
    this.email = entity.getUser().getEmail();
    this.title = entity.getTitle();
    this.likeNum = entity.getLikeNum();
    this.category = entity.getCategory();
    this.regDt = entity.getRegDt().format(DateTimeFormatter.ISO_DATE);
  }
}
