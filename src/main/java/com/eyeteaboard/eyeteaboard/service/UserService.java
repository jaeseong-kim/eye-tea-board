package com.eyeteaboard.eyeteaboard.service;

import com.eyeteaboard.eyeteaboard.config.CustomUserDetails;
import com.eyeteaboard.eyeteaboard.dto.AdminBanUserResDto;
import com.eyeteaboard.eyeteaboard.dto.AdminFindUserDto;
import com.eyeteaboard.eyeteaboard.dto.AdminFindUserInfoDto;
import com.eyeteaboard.eyeteaboard.dto.AuthResDto;
import com.eyeteaboard.eyeteaboard.dto.OAuthRegisterReqDto;
import com.eyeteaboard.eyeteaboard.dto.OAuthRegisterResDto;
import com.eyeteaboard.eyeteaboard.dto.RegisterReqDto;
import com.eyeteaboard.eyeteaboard.dto.RegisterResDto;
import com.eyeteaboard.eyeteaboard.entity.User;
import com.eyeteaboard.eyeteaboard.repository.UserRepository;
import com.eyeteaboard.eyeteaboard.type.Error;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
  private final String LOCAL_ADDRESS = "http://localhost:8080";

  private final String SERVER_ADDRESS = "http://ec2-43-200-186-103.ap-northeast-2.compute.amazonaws.com";

  private final PasswordEncoder passwordEncoder;

  private final JavaMailSender mailSender;

  private final UserRepository userRepository;

  @Transactional
  public RegisterResDto register(RegisterReqDto parameter) {
    if (userRepository.existsById(parameter.getEmail())) {
      return RegisterResDto.builder()
                           .status(false)
                           .message("중복된 이메일입니다.")
                           .build();
    }

    // 이메일 인증키 생성
    String uuid = UUID.randomUUID()
                      .toString();
    parameter.insertAuthKey(uuid);

    // 비밀번호 암호화
    parameter.encodePassword(passwordEncoder.encode(parameter.getPassword()));

    // 유저 등록하기
    userRepository.save(parameter.toEntity());

    // 인증 이메일 보내기
    try {
      MimeMessage msg = mailSender.createMimeMessage();
      MimeMessageHelper msgHelper = new MimeMessageHelper(msg, "UTF-8");

      String text = "<html>"
          + "<head>"
          + "</head>"
          + "<body>"
          + "<a href=" + SERVER_ADDRESS + "/user/auth/" + uuid + ">인증하기</a>"
          + "</body>"
          + "</html>";

      msgHelper.setFrom("thecastleexists@gmail.com");
      msgHelper.setTo(parameter.getEmail());
      msgHelper.setSubject("EyeTea Board 회원가입 인증메일");
      msgHelper.setText(text, true);
      mailSender.send(msg);

    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }

    return RegisterResDto.builder()
                         .status(true)
                         .message("등록된 이메일에서 인증을 진행해주세요,")
                         .build();
  }


  @Transactional
  public AuthResDto authEmail(String uuid) {
    Optional<User> optionalUser = userRepository.findByAuthKey(uuid);
    if (optionalUser.isEmpty()) {
      log.info(uuid);
      return AuthResDto.builder()
                       .status(false)
                       .message("유효하지 않은 인증키입니다.")
                       .build();
    }

    User user = optionalUser.get();
    if (user.isAuthYn()) {
      return AuthResDto.builder()
                       .status(false)
                       .message("이메일 인증이 완료된 이메일입니다.")
                       .build();
    }

    user.permitAuth();

    return AuthResDto.builder()
                     .status(true)
                     .message("인증이 완료되었습니다.")
                     .build();
  }


  @Transactional
  public OAuthRegisterResDto oauthRegister(OAuthRegisterReqDto parameter) {
    Optional<User> optionalUser = userRepository.findByEmail(parameter.getEmail());
    if (optionalUser.isEmpty()) {
      return OAuthRegisterResDto.builder()
                                .status(false)
                                .message("해당 유저가 없습니다.")
                                .build();
    }

    User user = optionalUser.get();

    //더티체킹
    user.registerOAuthGuest(parameter);

    return OAuthRegisterResDto.builder()
                              .status(true)
                              .message("회원가입이 완료되었습니다. 다시 로그인 해주세요.")
                              .build();
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isEmpty()) {
      throw new UsernameNotFoundException(Error.NO_USER.getMessage());
    }

    User user = optionalUser.get();

    return new CustomUserDetails(user);
  }

  public List<AdminFindUserDto> findAllUser() {
    List<User> userList = userRepository.findAll();

    List<AdminFindUserDto> adminAllUserDtoList = new ArrayList<>();
    for (int i = 0; i < userList.size(); i++) {
      User user = userList.get(i);
      adminAllUserDtoList.add(AdminFindUserDto.builder()
                                              .email(user.getEmail())
                                              .build());
    }

    return adminAllUserDtoList;

  }

  public List<AdminFindUserDto> findUser(String email) {
    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isEmpty()) {
      return null;
    }

    User user = optionalUser.get();
    List<AdminFindUserDto> adminFindUserDtoList = new ArrayList<>();
    adminFindUserDtoList.add(AdminFindUserDto.builder()
                                             .email(user.getEmail())
                                             .build());

    return adminFindUserDtoList;
  }

  public AdminFindUserInfoDto findUserInfo(String email) {
    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isEmpty()) {
      return null;
    }

    return new AdminFindUserInfoDto(optionalUser.get());
  }

  @Transactional
  public AdminBanUserResDto changeUserStatus(String email) {
    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isEmpty()) {
      return AdminBanUserResDto.builder()
                               .status(false)
                               .message("유저가 없습니다.")
                               .build();
    }

    User user = optionalUser.get();

    if (user.isBan()) {
      user.stopBan();

      return AdminBanUserResDto.builder()
                               .status(true)
                               .message("유저의 계정 정지가 풀렸습니다.")
                               .build();
    }

    user.ban();

    return AdminBanUserResDto.builder()
                             .status(true)
                             .message("유저의 계정을 정지했습니다.")
                             .build();
  }
}
