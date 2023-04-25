package com.eyeteaboard.eyeteaboard.controller;

import com.eyeteaboard.eyeteaboard.service.CommentService;
import com.eyeteaboard.eyeteaboard.service.PostService;
import com.eyeteaboard.eyeteaboard.type.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
@Controller
public class PostController {

  private final PostService postService;

  private final CommentService commentService;

  @GetMapping("/save")
  public String postSave() {
    return "post/save-post";
  }

  @GetMapping(value = {"/list/{category}", "/list"})
  public String postAllList(Model model, @PathVariable(required = false) Category category,
      @RequestParam(name = "sort", required = false) String sort) {

    log.info("category : " + category + " sort : " + sort);

    model.addAttribute("list", postService.postListByCategoryAndSort(category, sort));

    return "list";
  }


  @GetMapping("/{id}")
  public String viewPost(Model model, @PathVariable Long id) {
    model.addAttribute("post", postService.findPost(id));
    model.addAttribute("comments", commentService.findCommentList(id));
    return "post/view-post";
  }

  @GetMapping("/update/{id}")
  public String updatePost(Model model, @PathVariable Long id) {
    model.addAttribute("post", postService.findPost(id));

    return "post/update-post";
  }
}
