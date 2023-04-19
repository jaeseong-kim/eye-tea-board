package com.eyeteaboard.eyeteaboard.controller;

import com.eyeteaboard.eyeteaboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

  private final UserService userService;

  @GetMapping("/user/register")
  public String register() {
    return "user/register";
  }

  @GetMapping("/user/auth/{authKey}")
  public String emailAuth(Model model, @PathVariable(value = "authKey") String authKey) {

    log.info("authKey : " + authKey);

    model.addAttribute("response", userService.authEmail(authKey));

    return "user/auth";
  }
}
