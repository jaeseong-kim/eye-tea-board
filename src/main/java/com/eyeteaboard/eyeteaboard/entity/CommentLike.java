package com.eyeteaboard.eyeteaboard.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class CommentLike extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long commentLikeId;

  @ManyToOne
  @JoinColumn(name = "commentId")
  private Comment commentId;

  @ManyToOne
  @JoinColumn(name = "email")
  private User clicker;

  @Builder
  public CommentLike(Comment commentId, User clicker) {
    this.commentId = commentId;
    this.clicker = clicker;
  }

}
