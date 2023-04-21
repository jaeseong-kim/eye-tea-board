package com.eyeteaboard.eyeteaboard.dto;


import com.eyeteaboard.eyeteaboard.entity.Post;
import com.eyeteaboard.eyeteaboard.type.Category;
import lombok.Getter;

@Getter
public class PostResDto {
  private String title;
  private String content;
  private int likeNum;

  private Category category;

  public PostResDto(Post entity){
    this.title = entity.getTitle();
    this.content = entity.getContent();
    this.likeNum = entity.getLikeNum();
    this.category = entity.getCategory();
  }
}
