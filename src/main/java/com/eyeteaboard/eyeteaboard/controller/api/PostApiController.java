package com.eyeteaboard.eyeteaboard.controller.api;

import com.eyeteaboard.eyeteaboard.dto.PostSaveReqDto;
import com.eyeteaboard.eyeteaboard.dto.PostSaveResDto;
import com.eyeteaboard.eyeteaboard.service.PostService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/post")
@RestController
public class PostApiController {

  private final PostService postService;


  @PostMapping("/save")
  public PostSaveResDto postSave(@RequestBody PostSaveReqDto parameter, Principal principal) {

    //로그인한 사용자 가져오기
    String email = "";
    return postService.postSave(parameter, email);
  }
}
