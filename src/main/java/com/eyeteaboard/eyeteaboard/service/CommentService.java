package com.eyeteaboard.eyeteaboard.service;

import com.eyeteaboard.eyeteaboard.dto.CommentResDto;
import com.eyeteaboard.eyeteaboard.dto.CommentSaveReqDto;
import com.eyeteaboard.eyeteaboard.dto.CommentSaveResDto;
import com.eyeteaboard.eyeteaboard.entity.Comment;
import com.eyeteaboard.eyeteaboard.entity.Post;
import com.eyeteaboard.eyeteaboard.entity.User;
import com.eyeteaboard.eyeteaboard.repository.CommentRepository;
import com.eyeteaboard.eyeteaboard.repository.PostRepository;
import com.eyeteaboard.eyeteaboard.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

  private final UserRepository userRepository;

  private final PostRepository postRepository;
  private final CommentRepository commentRepository;

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

}
