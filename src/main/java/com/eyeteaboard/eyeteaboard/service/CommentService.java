package com.eyeteaboard.eyeteaboard.service;

import com.eyeteaboard.eyeteaboard.dto.CommentDelResDto;
import com.eyeteaboard.eyeteaboard.dto.CommentLikeResDto;
import com.eyeteaboard.eyeteaboard.dto.CommentResDto;
import com.eyeteaboard.eyeteaboard.dto.CommentSaveReqDto;
import com.eyeteaboard.eyeteaboard.dto.CommentSaveResDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
      //해당 댓글이 없음
    }

    Comment comment = optionalComment.get();

    commentLikeRepository.deleteAllByCommentId(comment);
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
}
