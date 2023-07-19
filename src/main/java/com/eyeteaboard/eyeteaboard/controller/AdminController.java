package com.eyeteaboard.eyeteaboard.controller;

import com.eyeteaboard.eyeteaboard.dto.AdminFindUserDto;
import com.eyeteaboard.eyeteaboard.dto.CommentResDto;
import com.eyeteaboard.eyeteaboard.dto.PageInfoDto;
import com.eyeteaboard.eyeteaboard.dto.PostListResDto;
import com.eyeteaboard.eyeteaboard.service.CommentService;
import com.eyeteaboard.eyeteaboard.service.PostService;
import com.eyeteaboard.eyeteaboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
   * 관리자 메인 페이지로 모든 회원 리스트 페이지를 반환합니다. email로 원하는 사용자를 검색할 수 있습니다.
   *
   * @param email - 사용자 이메일 email이 존재하면 해당 사용자가 출력됩니다.
   * @param page  - 페이지 번호 default 값은 0 입니다.
   * @return
   */
  @GetMapping("/admin")
  public String admin(Model model, @RequestParam(required = false) String email,
      @RequestParam(defaultValue = "0") int page) {

    page = page > 0 ? page - 1 : 0;

    if (email != null) {
      model.addAttribute("list", userService.findUser(email, page));
    } else {

      Page<AdminFindUserDto> pageUsers = userService.findAllUser(page);
      PageInfoDto pageInfoDto = userService.getPageInfo(pageUsers);

      model.addAttribute("list", pageUsers);
      model.addAttribute("pageInfo", pageInfoDto);
    }

    return "admin/main";
  }

  /**
   * 사용자의 개인정보 페이지를 반환합니다.
   *
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
   *
   * @param email 사용자 이메일
   * @return admin/posts
   */
  @GetMapping("/admin/posts/{email}")
  public String posts(Model model, @PathVariable String email,
      @RequestParam(defaultValue = "0") int page) {

    page = page > 0 ? page - 1 : 0;

    Page<PostListResDto> pagePosts = postService.getPagePostsByEmail(page, email);
    PageInfoDto pageInfo = postService.getPageInfo(pagePosts);

    model.addAttribute("posts", pagePosts.getContent());
    model.addAttribute("pageInfo", pageInfo);
    model.addAttribute("email", email);

    return "admin/posts";
  }

  /**
   * 사용자의 댓글 리스트를 반환합니다.
   *
   * @param email
   * @return
   */
  @GetMapping("/admin/comments/{email}")
  public String comments(Model model, @PathVariable String email,
      @RequestParam(defaultValue = "0") int page) {

    page = page > 0 ? page - 1 : 0;

    Page<CommentResDto> commentsPage = commentService.getPageCommentsByEmail(page, email);
    PageInfoDto pageInfo = commentService.getPageInfoDto(commentsPage);

    model.addAttribute("comments", commentsPage.getContent());
    model.addAttribute("pageInfo", pageInfo);
    return "admin/comments";
  }
}
