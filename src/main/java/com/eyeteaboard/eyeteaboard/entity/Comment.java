package com.eyeteaboard.eyeteaboard.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long commentId;

  @ManyToOne
  @JoinColumn(name = "postId")
  private Post postId;

  @ManyToOne
  @JoinColumn(name = "email")
  private User writer;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String comment;

  @ColumnDefault("0")
  @Column(nullable = false)
  private int likeNum;


  @Builder
  public Comment(Post postId, User writer, String comment, int likeNum) {
    this.postId = postId;
    this.writer = writer;
    this.comment = comment;
    this.likeNum = likeNum;
  }


  public void updateLikeNum(int likeNum){
    this.likeNum = likeNum;
  }
}
