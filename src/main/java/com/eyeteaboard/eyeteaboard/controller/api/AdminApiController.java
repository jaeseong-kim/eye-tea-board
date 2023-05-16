package com.eyeteaboard.eyeteaboard.controller.api;

import com.eyeteaboard.eyeteaboard.dto.AdminBanUserResDto;
import com.eyeteaboard.eyeteaboard.dto.CommentDelResDto;
import com.eyeteaboard.eyeteaboard.dto.PostDeleteResDto;
import com.eyeteaboard.eyeteaboard.service.CommentService;
import com.eyeteaboard.eyeteaboard.service.PostService;
import com.eyeteaboard.eyeteaboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AdminApiController {

  private final PostService postService;

  private final CommentService commentService;

  private final UserService userService;

  @PutMapping("/admin/users/status/{email}")
  public AdminBanUserResDto changeUserStatus(@PathVariable String email) {
    return userService.changeUserStatus(email);
  }

  @DeleteMapping("/admin/posts/{id}")
  public PostDeleteResDto deletePost(@PathVariable Long id) {
    return postService.deletePost(id);
  }

  @DeleteMapping("/admin/comments/{id}")
  public CommentDelResDto deleteComment(@PathVariable Long id) {
    return commentService.deleteComment(id);
  }


}
