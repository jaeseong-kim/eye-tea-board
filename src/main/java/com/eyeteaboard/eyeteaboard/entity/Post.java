package com.eyeteaboard.eyeteaboard.entity;


import com.eyeteaboard.eyeteaboard.dto.PostUpdateReqDto;
import com.eyeteaboard.eyeteaboard.type.Category;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postId;

  @ManyToOne // 유저 테이블(one) - 게시글 테이블(many), 게시글 테이블이 유저테이블의 개인키를 참조한다.
  @JoinColumn(name = "email")
  private User user;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @ColumnDefault("0")
  @Column(nullable = false)
  private int likeNum;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Category category;

  @Builder
  public Post(User email, String title, String content, int likeNum, Category category) {
    this.user = email;
    this.title = title;
    this.content = content;
    this.likeNum = likeNum;
    this.category = category;
  }

  public void update(PostUpdateReqDto dto){
    this.title = dto.getTitle();
    this.category = Category.valueOf(dto.getCategory());
    this.content = dto.getContent();
  }

  public void updateLikeNum(int likeNum){
    this.likeNum = likeNum;
  }
}
