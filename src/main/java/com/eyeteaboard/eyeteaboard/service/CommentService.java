package com.eyeteaboard.eyeteaboard.service;

import com.eyeteaboard.eyeteaboard.dto.CommentDelResDto;
import com.eyeteaboard.eyeteaboard.dto.CommentLikeResDto;
import com.eyeteaboard.eyeteaboard.dto.CommentResDto;
import com.eyeteaboard.eyeteaboard.dto.CommentSaveReqDto;
import com.eyeteaboard.eyeteaboard.dto.CommentSaveResDto;
import com.eyeteaboard.eyeteaboard.dto.PageInfoDto;
import com.eyeteaboard.eyeteaboard.entity.Comment;
import com.eyeteaboard.eyeteaboard.entity.CommentLike;
import com.eyeteaboard.eyeteaboard.entity.Post;
import com.eyeteaboard.eyeteaboard.entity.User;
import com.eyeteaboard.eyeteaboard.repository.CommentLikeRepository;
import com.eyeteaboard.eyeteaboard.repository.CommentRepository;
import com.eyeteaboard.eyeteaboard.repository.PostRepository;
import com.eyeteaboard.eyeteaboard.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

  private final UserRepository userRepository;

  private final PostRepository postRepository;
  private final CommentRepository commentRepository;

  private final CommentLikeRepository commentLikeRepository;

  public List<CommentResDto> findCommentList(Long postId) {
    Optional<Post> optionalPost = postRepository.findById(postId);
    if (optionalPost.isEmpty()) {
      // 해당 게시글 없음
    }

    Post post = optionalPost.get();

    List<Comment> entityList = commentRepository.findAllByPostId(post);

    List<CommentResDto> dtoList = new ArrayList<>();
    for (int i = 0; i < entityList.size(); i++) {
      Comment comment = entityList.get(i);
      dtoList.add(new CommentResDto(comment));
    }

    return dtoList;
  }

  public List<CommentResDto> findCommentsByEmail(String email) {
    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isEmpty()) {
      //유저 없음
    }

    List<Comment> commentList = commentRepository.findAllByWriter(optionalUser.get());

    List<CommentResDto> commentResDtoList = new ArrayList<>();
    for (int i = 0; i < commentList.size(); i++) {
      commentResDtoList.add(new CommentResDto(commentList.get(i)));
    }

    return commentResDtoList;
  }

  public CommentSaveResDto saveComment(CommentSaveReqDto dto, String writer) {
    Optional<Post> optionalPost = postRepository.findById(dto.getPostId());
    if (optionalPost.isEmpty()) {
      // 게시글 없음
    }

    Optional<User> optionalUser = userRepository.findByEmail(writer);
    if (optionalUser.isEmpty()) {
      // 유저 없음
    }

    commentRepository.save(Comment.builder()
                                  .postId(optionalPost.get())
                                  .writer(optionalUser.get())
                                  .comment(dto.getComment())
                                  .likeNum(0)
                                  .build());

    return CommentSaveResDto.builder()
                            .status(true)
                            .message("댓글을 달았습니다.")
                            .build();
  }

  @Transactional
  public CommentDelResDto deleteComment(Long commentId) {
    Optional<Comment> optionalComment = commentRepository.findById(commentId);
    if (optionalComment.isEmpty()) {
      return CommentDelResDto.builder()
                             .status(false)
                             .message("해당 댓글이 없습니다.")
                             .build();
    }

    Comment comment = optionalComment.get();

    // 삭제할 댓글에 좋아요 삭제
    commentLikeRepository.deleteAllByCommentId(comment);

    // 해당 댓글 삭제
    commentRepository.delete(comment);

    return CommentDelResDto.builder()
                           .status(true)
                           .message("댓글이 삭제되었습니다.")
                           .build();
  }

  public CommentLikeResDto clickCommentLike(Long commentId, String clicker) {

    Optional<Comment> optionalComment = commentRepository.findById(commentId);
    if (optionalComment.isEmpty()) {
      // 댓글이 없음
    }

    Optional<User> optionalUser = userRepository.findByEmail(clicker);
    if (optionalComment.isEmpty()) {
      // 유저가 없음
    }

    Comment comment = optionalComment.get();

    commentLikeRepository.save(CommentLike.builder()
                                          .commentId(comment)
                                          .clicker(optionalUser.get())
                                          .build());

    int likeNum = commentLikeRepository.countByCommentId(comment);
    comment.updateLikeNum(likeNum);

    return CommentLikeResDto.builder()
                            .status(true)
                            .likeNum(likeNum)
                            .message("댓글 좋아요를 눌렀습니다.")
                            .build();
  }

  public Page<CommentResDto> getPageCommentsByEmail(int page, String email) {
    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isEmpty()) {

    }

    final int SIZE_PER_PAGE = 10;

    Pageable pageable = PageRequest.of(page, SIZE_PER_PAGE);

    User user = optionalUser.get();
    return commentRepository.findAllByWriterOrderByCommentIdDesc(pageable, user)
                            .map(CommentResDto::new);
  }

  public PageInfoDto getPageInfoDto(Page<CommentResDto> page){
    final int COUNT_LIST = 5;

    int currentPage = page.getNumber() + 1;
    int startPage = (((currentPage - 1) / COUNT_LIST) * COUNT_LIST + 1);
    int endPage = Math.min(startPage + COUNT_LIST - 1, page.getTotalPages());

    log.info("시작 페이지 : " + startPage);
    log.info("현재 페이지 : " + currentPage);
    log.info("끝 페이지 : " + endPage);

    return new PageInfoDto(currentPage, startPage, endPage, page.hasPrevious(),
        page.hasNext());
  }

}
