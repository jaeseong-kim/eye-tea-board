package com.eyeteaboard.eyeteaboard.dto;

import com.eyeteaboard.eyeteaboard.entity.Comment;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class CommentResDto {

  private Long commentId;
  private Long postId;

  private String writer;

  private String comment;

  private int likeNum;

  private String regDt;

  public CommentResDto(Comment entity) {
    this.commentId = entity.getCommentId();
    this.postId = entity.getPostId().getPostId();
    this.writer = entity.getWriter().getEmail();
    this.comment = entity.getComment();
    this.likeNum = entity.getLikeNum();
    this.regDt = entity.getRegDt().format(DateTimeFormatter.ISO_DATE);
  }
}
