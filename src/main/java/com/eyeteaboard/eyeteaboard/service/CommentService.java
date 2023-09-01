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
import com.eyeteaboard.eyeteaboard.exception.NoCommentException;
import com.eyeteaboard.eyeteaboard.exception.NoPostException;
import com.eyeteaboard.eyeteaboard.exception.NoUserException;
import com.eyeteaboard.eyeteaboard.repository.CommentLikeRepository;
import com.eyeteaboard.eyeteaboard.repository.CommentRepository;
import com.eyeteaboard.eyeteaboard.repository.PostRepository;
import com.eyeteaboard.eyeteaboard.repository.UserRepository;
import com.eyeteaboard.eyeteaboard.type.Error;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {
  private final int SIZE_PER_PAGE = 10;

  private final UserRepository userRepository;

  private final PostRepository postRepository;
  private final CommentRepository commentRepository;

  private final CommentLikeRepository commentLikeRepository;

  public List<CommentResDto> findCommentList(Long id) {

    Post post = findPostById(id);

    List<Comment> entityList = commentRepository.findAllByPostId(post);

    List<CommentResDto> dtoList = new ArrayList<>();
    for (int i = 0; i < entityList.size(); i++) {
      Comment comment = entityList.get(i);
      dtoList.add(new CommentResDto(comment));
    }

    return dtoList;
  }

  public CommentSaveResDto saveComment(CommentSaveReqDto dto, String email) {

    Post post = findPostById(dto.getPostId());

    User user = findUserByEmail(email);

    commentRepository.save(Comment.builder()
                                  .postId(post)
                                  .writer(user)
                                  .comment(dto.getComment())
                                  .likeNum(0)
                                  .build());

    return CommentSaveResDto.builder()
                            .status(true)
                            .message("댓글을 달았습니다.")
                            .build();
  }

  @Transactional
  public CommentDelResDto deleteComment(Long id) {

    Comment comment = findCommentById(id);

    // 삭제할 댓글에 좋아요 삭제
    commentLikeRepository.deleteAllByCommentId(comment);

    // 해당 댓글 삭제
    commentRepository.delete(comment);

    return CommentDelResDto.builder()
                           .status(true)
                           .message("댓글이 삭제되었습니다.")
                           .build();
  }

  @Transactional
  public CommentLikeResDto clickCommentLike(Long id, String email) {

    Comment comment = findCommentById(id);

    User user = findUserByEmail(email);

    commentLikeRepository.save(CommentLike.builder()
                                          .commentId(comment)
                                          .clicker(user)
                                          .build());

    int likeNum = commentLikeRepository.countByCommentId(comment);

    comment.updateLikeNum(likeNum);

    return CommentLikeResDto.builder()
                            .status(true)
                            .likeNum(likeNum)
                            .message("댓글 좋아요를 눌렀습니다.")
                            .build();
  }

  @PreAuthorize("authentication.name == #email or hasAuthority('ROLE_ADMIN')")
  public Page<CommentResDto> getPageCommentsByEmail(int page, String email) {

    User user = findUserByEmail(email);

    final int SIZE_PER_PAGE = 10;

    Pageable pageable = PageRequest.of(page, SIZE_PER_PAGE);

    return commentRepository.findAllByWriterOrderByCommentIdDesc(pageable, user)
                            .map(CommentResDto::new);
  }

  public PageInfoDto getPageInfoDto(Page<CommentResDto> page){
    final int COUNT_LIST = 5;

    int currentPage = page.getNumber() + 1;
    int startPage = (((currentPage - 1) / COUNT_LIST) * COUNT_LIST + 1);
    int endPage = Math.min(startPage + COUNT_LIST - 1, page.getTotalPages());

    return new PageInfoDto(currentPage, startPage, endPage, page.hasPrevious(),
        page.hasNext());
  }
  private User findUserByEmail(String email){
    return userRepository.findByEmail(email).orElseThrow(() -> new NoUserException(Error.NO_USER));
  }

  private Post findPostById(Long id){
    return postRepository.findById(id).orElseThrow(() -> new NoPostException(Error.NO_EXISTS_POST));
  }

  private Comment findCommentById(Long id){
    return commentRepository.findById(id).orElseThrow(() -> new NoCommentException(Error.NO_COMMENT));
  }
}
