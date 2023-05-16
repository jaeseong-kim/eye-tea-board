package com.eyeteaboard.eyeteaboard.repository;

import com.eyeteaboard.eyeteaboard.entity.Post;
import com.eyeteaboard.eyeteaboard.entity.User;
import com.eyeteaboard.eyeteaboard.type.Category;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findAllByCategory(Category category);
  List<Post> findAllByCategory(Category category, Sort sort);

  List<Post> findAllByUser(User user);
}
