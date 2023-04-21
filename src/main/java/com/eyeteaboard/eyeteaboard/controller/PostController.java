package com.eyeteaboard.eyeteaboard.controller;

import com.eyeteaboard.eyeteaboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/post")
@Controller
public class PostController {

  private final PostService postService;

  @GetMapping("/save")
  public String postSave() {
    return "post/save-post";
  }

  @GetMapping("/list")
  public String postList(Model model) {
    model.addAttribute("list", postService.findAll());
    return "list";
  }

  @GetMapping("/{id}")
  public String viewPost(Model model, @PathVariable Long id) {
    model.addAttribute("post", postService.findPost(id));

    return "post/view-post";
  }

  @GetMapping("/update/{id}")
  public String updatePost(Model model,@PathVariable Long id){
    model.addAttribute("post",postService.findPost(id));

    return "post/update-post";
  }
}
