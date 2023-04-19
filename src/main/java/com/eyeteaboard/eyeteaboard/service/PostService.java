package com.eyeteaboard.eyeteaboard.service;

import com.eyeteaboard.eyeteaboard.dto.PostSaveReqDto;
import com.eyeteaboard.eyeteaboard.dto.PostSaveResDto;
import com.eyeteaboard.eyeteaboard.entity.Post;
import com.eyeteaboard.eyeteaboard.entity.User;
import com.eyeteaboard.eyeteaboard.repository.PostRepository;
import com.eyeteaboard.eyeteaboard.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public PostSaveResDto postSave(PostSaveReqDto parameter, String email) {
    Optional<User> optionalUser = userRepository.findByEmail("doctorwho123@naver.com"); // 로그인 기능 이후에 수정
    if (optionalUser.isEmpty() || !optionalUser.get().isAuthYn()) {
      return PostSaveResDto.builder()
                            .status(false)
                            .message("존재하지 않는 시용자입니다.")
                            .build();
    }

    Post post = parameter.toEntity(optionalUser.get());

    //저장
    postRepository.save(post);

    return PostSaveResDto.builder()
                          .status(true)
                          .message("글이 등록되었습니다.")
                          .build();
  }
}
