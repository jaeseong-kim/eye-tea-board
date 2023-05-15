package com.eyeteaboard.eyeteaboard.controller;

import com.eyeteaboard.eyeteaboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminController {

  private final UserService userService;

  @GetMapping("/admin")
  public String admin(Model model, @RequestParam(required = false) String email) {

    log.info("찾을 이메일 : " + email);
    if (email != null) {
      model.addAttribute("list", userService.findUser(email));
    } else {
      model.addAttribute("list", userService.findAllUser());
    }

    return "admin/main";
  }

  @GetMapping("/admin/users/{email}")
  public String userInfo(Model model, @PathVariable String email) {
    model.addAttribute("user", userService.findUserInfo(email));
    return "admin/userInfo";
  }


}
