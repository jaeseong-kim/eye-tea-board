package com.eyeteaboard.eyeteaboard.repository;

import com.eyeteaboard.eyeteaboard.entity.Post;
import com.eyeteaboard.eyeteaboard.entity.PostLike;
import com.eyeteaboard.eyeteaboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {

  int countAllByPostId(Post postId);
  boolean existsByPostIdAndClicker(Post postId, User clicker);

  void deleteAllByPostId(Post postId);
}
