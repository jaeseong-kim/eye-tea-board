package com.eyeteaboard.eyeteaboard.dto;


import com.eyeteaboard.eyeteaboard.entity.Post;
import com.eyeteaboard.eyeteaboard.type.Category;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class PostResDto {

  private Long id;
  private String writer;
  private String title;
  private String content;
  private int likeNum;

  private Category category;

  private String regDt;

  public PostResDto(Post entity) {
    this.id = entity.getPostId();
    this.writer = entity.getUser().getEmail();
    this.title = entity.getTitle();
    this.content = entity.getContent();
    this.likeNum = entity.getLikeNum();
    this.category = entity.getCategory();
    this.regDt = entity.getRegDt().format(DateTimeFormatter.ISO_DATE);
  }
}
