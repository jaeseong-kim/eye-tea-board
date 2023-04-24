package com.eyeteaboard.eyeteaboard.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class PostLike extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postLikeId;

  @ManyToOne
  @JoinColumn(name = "id")
  private Post postId;

  @ManyToOne
  @JoinColumn(name = "email")
  private User clicker;

  @Builder
  public PostLike(Post postId, User clicker) {
    this.postId = postId;
    this.clicker = clicker;
  }
}
