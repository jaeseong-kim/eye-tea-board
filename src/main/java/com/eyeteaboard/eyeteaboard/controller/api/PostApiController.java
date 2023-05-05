package com.eyeteaboard.eyeteaboard.controller.api;

import com.eyeteaboard.eyeteaboard.dto.PostDeleteResDto;
import com.eyeteaboard.eyeteaboard.dto.PostLikeResDto;
import com.eyeteaboard.eyeteaboard.dto.PostSaveReqDto;
import com.eyeteaboard.eyeteaboard.dto.PostSaveResDto;
import com.eyeteaboard.eyeteaboard.dto.PostUpdateReqDto;
import com.eyeteaboard.eyeteaboard.dto.PostUpdateResDto;
import com.eyeteaboard.eyeteaboard.service.PostService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    String email = principal.getName();
    return postService.postSave(parameter, email);
  }

  @PostMapping("/like/{id}")
  public PostLikeResDto clickPostLike(@PathVariable Long id, Principal principal) {
    //해당 유저 가져오기
    String email = principal.getName();
    PostLikeResDto postLikeResDto = postService.clickPostLike(id, email);
    return postLikeResDto;
  }

  @PutMapping("/update/{id}")
  public PostUpdateResDto updatePost(@PathVariable Long id,
      @RequestBody PostUpdateReqDto parameter) {
    return postService.updatePost(id, parameter);
  }

  @DeleteMapping("/delete/{id}")
  public PostDeleteResDto deletePost(@PathVariable Long id) {
    return postService.deletePost(id);
  }
}
