package com.eyeteaboard.eyeteaboard.controller;

import com.eyeteaboard.eyeteaboard.dto.CommentResDto;
import com.eyeteaboard.eyeteaboard.dto.PageInfoDto;
import com.eyeteaboard.eyeteaboard.dto.PostListResDto;
import com.eyeteaboard.eyeteaboard.service.CommentService;
import com.eyeteaboard.eyeteaboard.service.PostService;
import com.eyeteaboard.eyeteaboard.service.UserService;
import com.eyeteaboard.eyeteaboard.type.Login;
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
public class UserController {

  private final UserService userService;

  private final PostService postService;

  private final CommentService commentService;

  @GetMapping("/user/register")
  public String register() {
    return "user/register";
  }

  @GetMapping("/user/oauth-register")
  public String oauthRegister(Model model, @RequestParam String email) {

    model.addAttribute("email", email);

    return "user/oauth-register";
  }


  @GetMapping("/user/auth/{authKey}")
  public String emailAuth(Model model, @PathVariable(value = "authKey") String authKey) {

    log.info("authKey : " + authKey);

    model.addAttribute("response", userService.authEmail(authKey));

    return "user/auth";
  }

  @GetMapping("/user/my-page")
  public String myPage() {
    return "user/my-page";
  }

  @GetMapping("/user/my-page/edit/{email}")
  public String editProfile(Model model, @PathVariable String email) {

    model.addAttribute("user", userService.findUserInfo(email));
    model.addAttribute("OUR_SERVICE", Login.OUR_SERVICE);

    return "user/edit-info";
  }

  @GetMapping("/user/my-page/posts/{email}")
  public String myPostPage(Model model, @PathVariable String email,
      @RequestParam(defaultValue = "0") int page) {

    page = page > 0 ? page - 1 : 0;

    Page<PostListResDto> pagePosts = postService.getPagePostsByEmail(page, email);
    PageInfoDto pageInfoDto = postService.getPageInfo(pagePosts);

    model.addAttribute("posts", pagePosts);
    model.addAttribute("pageInfo", pageInfoDto);

    return "user/my-posts";
  }

  @GetMapping("/user/my-page/comments/{email}")
  public String myCommentPage(Model model, @PathVariable String email,
      @RequestParam(defaultValue = "0") int page) {

    page = page > 0 ? page - 1 : 0;

    Page<CommentResDto> pageComments = commentService.getPageCommentsByEmail(page, email);
    PageInfoDto pageInfo = commentService.getPageInfoDto(pageComments);

    model.addAttribute("comments", pageComments);
    model.addAttribute("pageInfo", pageInfo);

    return "user/my-comments";
  }
}
