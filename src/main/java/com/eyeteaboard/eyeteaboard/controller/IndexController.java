package com.eyeteaboard.eyeteaboard.controller;

import java.security.Principal;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {

  private final HttpSession httpSession;

  @GetMapping("/")
  public String index(Model model) {

    return "index";
  }

  @GetMapping("/login")
  public String login(Model model,
      @RequestParam(name = "error", required = false) boolean error,
      @RequestParam(name = "exception", required = false) String exception) {

    model.addAttribute("error", error);
    model.addAttribute("exception", exception);

    return "login";
  }

}
