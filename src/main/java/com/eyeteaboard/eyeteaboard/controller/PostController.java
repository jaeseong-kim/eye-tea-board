package com.eyeteaboard.eyeteaboard.controller;

import com.eyeteaboard.eyeteaboard.dto.PageInfoDto;
import com.eyeteaboard.eyeteaboard.dto.PostListResDto;
import com.eyeteaboard.eyeteaboard.service.CommentService;
import com.eyeteaboard.eyeteaboard.service.PostService;
import com.eyeteaboard.eyeteaboard.type.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PostController {

  private final PostService postService;

  private final CommentService commentService;

  /**
   * 게시글 글쓰기 페이지를 반환합니다.
   *
   * @return post/save-post
   */
  @GetMapping("/post/save")
  public String postSave() {
    return "post/save-post";
  }

  /**
   * 게시글 리스트 페이지를 반환합니다.
   *
   * @param category 게시글 카테고리 키워드
   * @param page     게시글 페이지 번호
   * @param sort     게시글 정렬 키워드
   * @return list
   */
  @GetMapping(value = {"/post/list/{category}", "/post/list"})
  public String list(Model model, @PathVariable(required = false) Category category,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "postId") String sort) {

    // page는 0부터 시작하기 때문에 1씩 줄여야 한다.
    page = page < 1 ? 0 : page - 1;

    // 페이지 요청
    Page<PostListResDto> pagePosts = postService.getPagePosts(category, page, sort);

    // 요청 페이지에 대한 정보 DTO
    PageInfoDto pageInfoDto = postService.getPageInfo(pagePosts);

    model.addAttribute("list", pagePosts.getContent());
    model.addAttribute("pageInfo", pageInfoDto);

    return "list";
  }

  /**
   * 특정 게시글을 반환합니다.
   *
   * @param id 게시글 번호
   * @return post/view-post
   */
  @GetMapping("/post/view/{id}")
  public String viewPost(Model model, @PathVariable Long id) {
    model.addAttribute("post", postService.findViewPost(id));
    model.addAttribute("comments", commentService.findCommentList(id));
    return "post/view-post";
  }

  /**
   * 게시글 수정페이지를 반환합니다.
   *
   * @param id 수정할 게시글 번호
   * @return post/update-post
   */
  @GetMapping("/post/update/{id}")
  public String updatePost(Model model, @PathVariable Long id) {

    model.addAttribute("post", postService.findUpdatePost(id));

    return "post/update-post";
  }
}
