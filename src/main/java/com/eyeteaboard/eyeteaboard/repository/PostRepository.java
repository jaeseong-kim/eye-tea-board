package com.eyeteaboard.eyeteaboard.repository;

import com.eyeteaboard.eyeteaboard.entity.Post;
import com.eyeteaboard.eyeteaboard.entity.User;
import com.eyeteaboard.eyeteaboard.type.Category;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


  Page<Post> findAll(Pageable pageable);

  Page<Post> findAllByUserOrderByPostIdDesc(Pageable pageable, User user);

  Page<Post> findAllByCategory(Category category, Pageable pageable);

  List<Post> findAllByUser(User user);
}
