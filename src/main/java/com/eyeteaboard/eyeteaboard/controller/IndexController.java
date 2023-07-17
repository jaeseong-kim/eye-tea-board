package com.eyeteaboard.eyeteaboard.controller;

import java.security.Principal;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {

  /**
   * 메인화면 페이지를 반환합니다.
   * @return index
   */
  @GetMapping("/")
  public String index() {

    return "index";
  }

  /**
   * 로그인 페이지를 반환합니다. 로그인에 실패했을 경우 error는 true가 되고 exception에
   * 에러 메시지가 담깁니다.
   * @param error false or true
   * @param exception 에러 메시지
   * @return login
   */
  @GetMapping("/justlogin")
  public String login(Model model,
      @RequestParam(name = "error", required = false) boolean error,
      @RequestParam(name = "exception", required = false) String exception) {

    model.addAttribute("error", error);
    model.addAttribute("exception", exception);

    return "login";
  }

  /**
   * 간편 로그인 실패 페이지를 반환합니다.
   * @param error true or false
   * @param exception 에러 메시지
   * @return oauth-fail
   */
  @GetMapping("/oauth-fail")
  public String oauthLoginFail(Model model,
      @RequestParam(name="error", required = false) boolean error,
      @RequestParam(name = "exception",required = false) String exception){

    model.addAttribute("error", error);
    model.addAttribute("exception", exception);

    return "oauth-fail";
  }
}
