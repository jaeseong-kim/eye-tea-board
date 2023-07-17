package com.eyeteaboard.eyeteaboard.controller.api;

import com.eyeteaboard.eyeteaboard.dto.PostDeleteResDto;
import com.eyeteaboard.eyeteaboard.dto.PostLikeResDto;
import com.eyeteaboard.eyeteaboard.dto.PostSaveReqDto;
import com.eyeteaboard.eyeteaboard.dto.PostSaveResDto;
import com.eyeteaboard.eyeteaboard.dto.PostUpdateReqDto;
import com.eyeteaboard.eyeteaboard.dto.PostUpdateResDto;
import com.eyeteaboard.eyeteaboard.service.PostService;
import java.security.Principal;
import javax.validation.Valid;
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

  /**
   * 게시글 저장 POST 요청 메소드입니다.
   * @param parameter 저장할 게시글 객체
   * @param principal 인증받은 사용자
   * @return
   */
  @PostMapping("/save")
  public PostSaveResDto postSave(@RequestBody @Valid PostSaveReqDto parameter, Principal principal) {

    // 로그인한 사용자 가져오기
    String email = principal.getName();
    return postService.postSave(parameter, email);
  }

  /**
   * 게시글의 좋아요 수를 올려주는 메소드입니다.
   * @param id 게시글 번호
   * @param principal 인증받은 사용자
   * @return
   */
  @PostMapping("/like/{id}")
  public PostLikeResDto clickPostLike(@PathVariable Long id, Principal principal) {

    // 해당 유저 가져오기
    String email = principal.getName();
    return postService.clickPostLike(id, email);
  }

  /**
   * 게시글 수정 POST 요청 메소드입니다.
   * @param id 게시글 번호
   * @param parameter 게시글 수정 객체
   * @return
   */
  @PutMapping("/update/{id}")
  public PostUpdateResDto updatePost(@PathVariable Long id,
      @RequestBody @Valid PostUpdateReqDto parameter) {
    return postService.updatePost(id, parameter);
  }

  @DeleteMapping("/delete/{id}")
  public PostDeleteResDto deletePost(@PathVariable Long id) {
    return postService.deletePost(id);
  }
}
