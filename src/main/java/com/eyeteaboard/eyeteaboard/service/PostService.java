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
import com.eyeteaboard.eyeteaboard.repository.CommentLikeRepository;
import com.eyeteaboard.eyeteaboard.repository.CommentRepository;
import com.eyeteaboard.eyeteaboard.repository.PostLikeRepository;
import com.eyeteaboard.eyeteaboard.repository.PostRepository;
import com.eyeteaboard.eyeteaboard.repository.UserRepository;
import com.eyeteaboard.eyeteaboard.type.Category;
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

  private final PostLikeRepository postLikeRepository;
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final CommentRepository commentRepository;

  private final CommentLikeRepository commentLikeRepository;

  public PostSaveResDto postSave(PostSaveReqDto parameter, String email) {

    Optional<User> optionalUser = userRepository.findByEmail(email);

    Post post = parameter.toEntity(optionalUser.get());

    //저장
    postRepository.save(post);

    return PostSaveResDto.builder()
                         .status(true)
                         .message("글이 등록되었습니다.")
                         .build();
  }

  public Page<PostListResDto> getPagePosts(Category category, int page, String sort) {

    // 페이지 당 보여질 게시글의 수
    final int SIZE = 10;

    // 요청할 페이지 만들기
    Pageable pageable = PageRequest.of(page, SIZE, Sort.by(sort).descending());

    log.info(
        "요청받은 페이지 번호 : " + pageable.getPageNumber() + ", 정렬 : " + sort + ", category : " + category);

    if (category == null) {
      return postRepository.findAll(pageable)
                           .map(PostListResDto::new);
    } else {
      return postRepository.findAllByCategory(category, pageable)
                           .map(PostListResDto::new);
    }
  }

  public PageInfoDto getPageInfo(Page<PostListResDto> page) {

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

  @Transactional
  public PostResDto findViewPost(Long id) {
    Optional<Post> optionalPost = postRepository.findById(id);
    if (optionalPost.isEmpty()) {
      //예외처리로
    }

    Post post = optionalPost.get();
    int likeNum = postLikeRepository.countAllByPostId(post);
    post.updateLikeNum(likeNum); //더티체킹

    return new PostResDto(post);
  }

  @PostAuthorize("returnObject.writer == authentication.name")
  public PostResDto findUpdatePost(Long id) {
    Optional<Post> optionalPost = postRepository.findById(id);
    if (optionalPost.isEmpty()) {
      //
    }

    return new PostResDto(optionalPost.get());
  }

  @Transactional
  public PostUpdateResDto updatePost(Long id, PostUpdateReqDto parameter) {
    if (parameter.getCategory() == null) {
      return PostUpdateResDto.builder()
                             .status(false)
                             .message("키워드를 선택하세요.")
                             .build();
    }

    Optional<Post> optionalPost = postRepository.findById(id);
    if (optionalPost.isEmpty()) {
      return PostUpdateResDto.builder()
                             .status(false)
                             .message("해당 게시글이 없습니다.")
                             .build();
    }

    Post post = optionalPost.get();

    post.update(parameter);

    return PostUpdateResDto.builder()
                           .status(true)
                           .message("게시글이 수정되었습니다.")
                           .build();
  }

  @Transactional
  public PostDeleteResDto deletePost(Long id) {
    Optional<Post> optionalPost = postRepository.findById(id);
    if (optionalPost.isEmpty()) {
      return PostDeleteResDto.builder()
                             .status(false)
                             .message("해당 게시글이 없습니다.")
                             .build();
    }

    // 게시글 삭제하기 전에 외래키때문에 외래키들을 모두 제거해야한다.
    Post post = optionalPost.get();

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

  public PostLikeResDto clickPostLike(Long id, String email) {
    Optional<Post> optionalPost = postRepository.findById(id);
    if (optionalPost.isEmpty()) {
      return PostLikeResDto.builder()
                           .status(false)
                           .LikeNum(-1)
                           .message("해당 포스트가 없습니다.")
                           .build();
    }

    Optional<User> optionalUser = userRepository.findByEmail(email);
    Post post = optionalPost.get();
    User user = optionalUser.get();

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

  public List<PostResDto> findAllPostByEmail(String email) {
    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isEmpty()) {
      //
    }

    User user = optionalUser.get();

    List<PostResDto> postResDtoList = new ArrayList<>();

    List<Post> postList = postRepository.findAllByUser(user);
    for (int i = 0; i < postList.size(); i++) {
      postResDtoList.add(new PostResDto(postList.get(i)));
    }

    return postResDtoList;
  }
}
