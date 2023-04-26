package com.eyeteaboard.eyeteaboard.controller.api;

import com.eyeteaboard.eyeteaboard.dto.CommentDelResDto;
import com.eyeteaboard.eyeteaboard.dto.CommentLikeResDto;
import com.eyeteaboard.eyeteaboard.dto.CommentSaveReqDto;
import com.eyeteaboard.eyeteaboard.dto.CommentSaveResDto;
import com.eyeteaboard.eyeteaboard.service.CommentService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/comment")
@RestController
public class CommentApiController {

  private final CommentService commentService;

  @PostMapping("/save")
  public CommentSaveResDto saveComment(@RequestBody CommentSaveReqDto dto, Principal principal) {

    //String writer = principal.getName();
    return commentService.saveComment(dto, "doctorwho123@naver.com");
  }

  @DeleteMapping("/delete/{commentId}")
  public CommentDelResDto deleteComment(@PathVariable Long commentId) {
    return commentService.deleteComment(commentId);
  }

  @PostMapping("/like/{commentId}")
  public CommentLikeResDto clickCommentLike(@PathVariable Long commentId, Principal principal) {
    //String clicker = principal.getName();
    return commentService.clickCommentLike(commentId, "doctorwho123@naver.com");
  }
}
