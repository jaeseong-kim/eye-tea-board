package com.eyeteaboard.eyeteaboard.controller;

import com.eyeteaboard.eyeteaboard.service.CommentService;
import com.eyeteaboard.eyeteaboard.service.PostService;
import com.eyeteaboard.eyeteaboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminController {

  private final CommentService commentService;

  private final UserService userService;
  private final PostService postService;

  /**
   * 관리자 전용 페이지를 반환합니다. 기본적으로 모든 회원 리스트를 출력합니다.
   * @param email 검색할 때 사용되는 사용자 이메일
   * @return admin/main
   */
  @GetMapping("/admin")
  public String admin(Model model, @RequestParam(required = false) String email) {

    if (email != null) {
      model.addAttribute("list", userService.findUser(email));
    } else {
      model.addAttribute("list", userService.findAllUser());
    }

    return "admin/main";
  }

  /**
   * 사용자의 개인정보 페이지를 반환합니다.
   * @param email 사용자 이메일
   * @return
   */
  @GetMapping("/admin/users/{email}")
  public String userInfo(Model model, @PathVariable String email) {
    model.addAttribute("user", userService.findUserInfo(email));
    return "admin/userInfo";
  }

  /**
   * 사용자의 게시글 리스트 페이지를 반환합니다.
   * @param email 사용자 이메일
   * @return admin/posts
   */
  @GetMapping("/admin/posts/{email}")
  public String posts(Model model, @PathVariable String email){
    model.addAttribute("posts", postService.findAllPostByEmail(email));
    return "admin/posts";
  }

  /**
   * 사용자의 댓글 리스트를 반환합니다.
   * @param email
   * @return
   */
  @GetMapping("/admin/comments/{email}")
  public String comments(Model model, @PathVariable String email){
    model.addAttribute("comments",commentService.findCommentsByEmail(email));
    return "admin/comments";
  }
}
