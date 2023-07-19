package com.eyeteaboard.eyeteaboard.service;

import com.eyeteaboard.eyeteaboard.dto.PageInfoDto;
import com.eyeteaboard.eyeteaboard.dto.PostDeleteResDto;
import com.eyeteaboard.eyeteaboard.dto.PostLikeResDto;
import com.eyeteaboard.eyeteaboard.dto.PostListResDto;
import com.eyeteaboard.eyeteaboard.dto.PostResDto;
import com.eyeteaboard.eyeteaboard.dto.PostSaveReqDto;
import com.eyeteaboard.eyeteaboard.dto.PostSaveResDto;
import com.eyeteaboard.eyeteaboard.dto.PostUpdateReqDto;
import com.eyeteaboard.eyeteaboard.dto.PostUpdateResDto;
import com.eyeteaboard.eyeteaboard.entity.Comment;
import com.eyeteaboard.eyeteaboard.entity.Post;
import com.eyeteaboard.eyeteaboard.entity.PostLike;
import com.eyeteaboard.eyeteaboard.entity.User;
import com.eyeteaboard.eyeteaboard.exception.NoPostException;
import com.eyeteaboard.eyeteaboard.exception.NoUserException;
import com.eyeteaboard.eyeteaboard.repository.CommentLikeRepository;
import com.eyeteaboard.eyeteaboard.repository.CommentRepository;
import com.eyeteaboard.eyeteaboard.repository.PostLikeRepository;
import com.eyeteaboard.eyeteaboard.repository.PostRepository;
import com.eyeteaboard.eyeteaboard.repository.UserRepository;
import com.eyeteaboard.eyeteaboard.type.Category;
import com.eyeteaboard.eyeteaboard.type.Error;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

  private final int SIZE_PER_PAGE = 10;

  private final PostLikeRepository postLikeRepository;
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final CommentRepository commentRepository;

  private final CommentLikeRepository commentLikeRepository;

  public PostSaveResDto postSave(PostSaveReqDto parameter, String email) {

    User user = findUserByEmail(email);

    Post post = parameter.toEntity(user);

    // 저장
    postRepository.save(post);

    return PostSaveResDto.builder()
                         .status(true)
                         .message("글이 등록되었습니다.")
                         .build();
  }

  public Page<PostListResDto> getPagePosts(Category category, int page, String sort) {

    // 요청할 페이지 만들기
    Pageable pageable = PageRequest.of(page, SIZE_PER_PAGE, Sort.by(sort).descending());

    if (category == null) {
      return postRepository.findAll(pageable)
                           .map(PostListResDto::new);
    } else {
      return postRepository.findAllByCategory(category, pageable)
                           .map(PostListResDto::new);
    }
  }

  public Page<PostListResDto> getPagePostsByEmail(int page, String email) {

    User user = findUserByEmail(email);

    Pageable pageable = PageRequest.of(page, SIZE_PER_PAGE);

    return postRepository.findAllByUserOrderByPostIdDesc(pageable, user).map(PostListResDto::new);
  }

  public PageInfoDto getPageInfo(Page<PostListResDto> page) {

    final int COUNT_LIST = 5;

    int currentPage = page.getNumber() + 1;
    int startPage = (((currentPage - 1) / COUNT_LIST) * COUNT_LIST + 1);
    int endPage = Math.min(startPage + COUNT_LIST - 1, page.getTotalPages());

    return new PageInfoDto(currentPage, startPage, endPage, page.hasPrevious(),
        page.hasNext());
  }

  @Transactional
  public PostResDto findViewPost(Long id) {

    Post post = findPostById(id);

    int likeNum = postLikeRepository.countAllByPostId(post);

    // 더티체킹
    post.updateLikeNum(likeNum);

    return new PostResDto(post);
  }

  @PostAuthorize("returnObject.writer == authentication.name")
  public PostResDto findUpdatePost(Long id) {

    Post post = findPostById(id);

    return new PostResDto(post);
  }

  @Transactional
  public PostUpdateResDto updatePost(Long id, PostUpdateReqDto parameter) {

    Post post = findPostById(id);

    post.update(parameter);

    return PostUpdateResDto.builder()
                           .status(true)
                           .message("게시글이 수정되었습니다.")
                           .build();
  }

  @Transactional
  public PostDeleteResDto deletePost(Long id) {

    // 게시글 삭제하기 전에 외래키들을 모두 제거해야한다.
    Post post = findPostById(id);

    // 게시글에 달린 댓글들 찾기
    List<Comment> comments = commentRepository.findAllByPostId(post);

    // 게시글에 달린 좋아요 삭제
    postLikeRepository.deleteAllByPostId(post);

    // 게시글에 달린 댓글의 좋아요 삭제
    for (int i = 0; i < comments.size(); i++) {
      commentLikeRepository.deleteAllByCommentId(comments.get(i));
    }

    // 게시글에 달린 댓글 삭제
    commentRepository.deleteAllByPostId(post);

    // 게시글 삭제
    postRepository.delete(post);

    return PostDeleteResDto.builder()
                           .status(true)
                           .message("게시글이 삭제되었습니다.")
                           .build();
  }

  @Transactional
  public PostLikeResDto clickPostLike(Long id, String email) {

    Post post = findPostById(id);

    User user = findUserByEmail(email);

    boolean alreadyClick = postLikeRepository.existsByPostIdAndClicker(post, user);

    if (alreadyClick) {
      return PostLikeResDto.builder()
                           .status(false)
                           .LikeNum(-1)
                           .message("이미 눌렀습니다.")
                           .build();
    }

    postLikeRepository.save(PostLike.builder()
                                    .postId(post)
                                    .clicker(user)
                                    .build());

    int likeNum = postLikeRepository.countAllByPostId(post);

    post.updateLikeNum(likeNum);

    return PostLikeResDto.builder()
                         .status(true)
                         .LikeNum(likeNum)
                         .message("좋아요를 눌렀습니다.")
                         .build();
  }

  private Post findPostById(Long id){
    return postRepository.findById(id).orElseThrow(()->new NoPostException(Error.NO_EXISTS_POST));
  }

  private User findUserByEmail(String email){
    return userRepository.findByEmail(email).orElseThrow(() -> new NoUserException(Error.NO_USER));
  }
}
