package com.eyeteaboard.eyeteaboard.service;

import com.eyeteaboard.eyeteaboard.dto.PostDeleteResDto;
import com.eyeteaboard.eyeteaboard.dto.PostLikeResDto;
import com.eyeteaboard.eyeteaboard.dto.PostListResDto;
import com.eyeteaboard.eyeteaboard.dto.PostResDto;
import com.eyeteaboard.eyeteaboard.dto.PostSaveReqDto;
import com.eyeteaboard.eyeteaboard.dto.PostSaveResDto;
import com.eyeteaboard.eyeteaboard.dto.PostUpdateReqDto;
import com.eyeteaboard.eyeteaboard.dto.PostUpdateResDto;
import com.eyeteaboard.eyeteaboard.entity.Post;
import com.eyeteaboard.eyeteaboard.entity.PostLike;
import com.eyeteaboard.eyeteaboard.entity.User;
import com.eyeteaboard.eyeteaboard.repository.PostLikeRepository;
import com.eyeteaboard.eyeteaboard.repository.PostRepository;
import com.eyeteaboard.eyeteaboard.repository.UserRepository;
import com.eyeteaboard.eyeteaboard.type.Category;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

  private final PostLikeRepository postLikeRepository;
  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public PostSaveResDto postSave(PostSaveReqDto parameter, String email) {

    Optional<User> optionalUser = userRepository.findByEmail(
        "doctorwho123@naver.com"); // 로그인 기능 이후에 수정
    /*
    스프링 시큐리티에서 사용자를 관리해주기 때문에 필요없지 않나?

    if (optionalUser.isEmpty() || !optionalUser.get().isAuthYn()) {
      return PostSaveResDto.builder()
                            .status(false)
                            .message("존재하지 않는 시용자입니다.")
                            .build();
    }

     */

    Post post = parameter.toEntity(optionalUser.get());

    //저장
    postRepository.save(post);

    return PostSaveResDto.builder()
                         .status(true)
                         .message("글이 등록되었습니다.")
                         .build();
  }

  @Transactional
  public List<PostListResDto> postListByCategoryAndSort(Category category, String sort) {

    final String DEFAULT_ORDER = "postId";
    List<Post> postList;

    if (category == null && sort == null) {
      postList = postRepository.findAll(Sort.by(Direction.DESC, DEFAULT_ORDER));
    } else if (category != null) {
      if (sort == null) {
        postList = postRepository.findAllByCategory(category, Sort.by(Direction.DESC, DEFAULT_ORDER));
      } else {
        postList = postRepository.findAllByCategory(category, Sort.by(Direction.DESC, sort, DEFAULT_ORDER));
      }
    } else {
      postList = postRepository.findAll(Sort.by(Direction.DESC, sort, DEFAULT_ORDER));
    }

    List<PostListResDto> postListResDtoList = new ArrayList<>();
    for (int i = 0; i < postList.size(); i++) {
      Post post = postList.get(i);
      int likeNum = postLikeRepository.countAllByPostId(post);
      post.updateLikeNum(likeNum);
      postListResDtoList.add(new PostListResDto(post));
    }

    return postListResDtoList;
  }

  @Transactional
  public PostResDto findPost(Long id) {
    Optional<Post> optionalPost = postRepository.findById(id);
    if (optionalPost.isEmpty()) {
      //해당 게시글이 없습니다.
    }

    Post post = optionalPost.get();
    int likeNum = postLikeRepository.countAllByPostId(post);
    post.updateLikeNum(likeNum); //더티체킹

    return new PostResDto(post);
  }

  @Transactional
  public PostUpdateResDto update(Long id, PostUpdateReqDto parameter) {
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
  public PostDeleteResDto delete(Long id) {
    Optional<Post> optionalPost = postRepository.findById(id);
    if (optionalPost.isEmpty()) {
      return PostDeleteResDto.builder()
                             .status(false)
                             .message("해당 게시글이 없습니다.")
                             .build();
    }

    postRepository.delete(optionalPost.get());

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
}
