package com.eyeteaboard.eyeteaboard.controller.api;

import com.eyeteaboard.eyeteaboard.dto.CommentDelResDto;
import com.eyeteaboard.eyeteaboard.dto.CommentLikeResDto;
import com.eyeteaboard.eyeteaboard.dto.CommentSaveReqDto;
import com.eyeteaboard.eyeteaboard.dto.CommentSaveResDto;
import com.eyeteaboard.eyeteaboard.service.CommentService;
import java.security.Principal;
import javax.validation.Valid;
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

  /**
   * 댓글 저장 POST 요청 메소드입니다.
   * @param parameter 저장할 댓글 객체
   * @param principal 인증받은 사용자
   * @return
   */
  @PostMapping("/save")
  public CommentSaveResDto saveComment(@RequestBody @Valid CommentSaveReqDto parameter, Principal principal) {

    String writer = principal.getName();
    return commentService.saveComment(parameter, writer);
  }

  /**
   * 댓글을 삭제 DELETE 요청 메소드입니다.
   * @param commentId 삭제할 댓글 번호
   * @return
   */
  @DeleteMapping("/delete/{commentId}")
  public CommentDelResDto deleteComment(@PathVariable Long commentId) {
    return commentService.deleteComment(commentId);
  }

  /**
   * 댓글 좋아요 수 올려주는 POST 요청 메소드입니다.
   * @param commentId 댓글 번호
   * @param principal 인증받은 사용자
   * @return
   */
  @PostMapping("/like/{commentId}")
  public CommentLikeResDto clickCommentLike(@PathVariable Long commentId, Principal principal) {
    String clicker = principal.getName();
    return commentService.clickCommentLike(commentId, clicker);
  }
}
