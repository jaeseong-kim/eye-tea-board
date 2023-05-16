package com.eyeteaboard.eyeteaboard.repository;

import com.eyeteaboard.eyeteaboard.entity.Comment;
import com.eyeteaboard.eyeteaboard.entity.Post;
import com.eyeteaboard.eyeteaboard.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findAllByPostId(Post postId);

  List<Comment> findAllByWriter(User writer);

  void deleteAllByPostId(Post postId);
}
