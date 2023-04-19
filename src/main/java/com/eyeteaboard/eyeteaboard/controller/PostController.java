package com.eyeteaboard.eyeteaboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/post")
@Controller
public class PostController {

  @GetMapping("/save")
  public String postSave(){
    return "post/save";
  }

}
