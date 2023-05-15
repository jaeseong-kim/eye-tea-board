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

  @GetMapping("/")
  public String index() {

    return "index";
  }

  @GetMapping("/justlogin")
  public String login(Model model,
      @RequestParam(name = "error", required = false) boolean error,
      @RequestParam(name = "exception", required = false) String exception) {

    model.addAttribute("error", error);
    model.addAttribute("exception", exception);

    return "login";
  }

  @GetMapping("/oauth-fail")
  public String oauthLoginFail(Model model,
      @RequestParam(name="error", required = false) boolean error,
      @RequestParam(name = "exception",required = false) String exception){

    model.addAttribute("error", error);
    model.addAttribute("exception", exception);

    return "oauth-fail";
  }
}
