package com.eyeteaboard.eyeteaboard.controller.api;

import com.eyeteaboard.eyeteaboard.dto.AdminBanUserResDto;
import com.eyeteaboard.eyeteaboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AdminApiController {

  private final UserService userService;

  @PutMapping("/admin/users/status/{email}")
  public AdminBanUserResDto chageUserStatus(@PathVariable String email) {
    return userService.changeUserStatus(email);
  }


}
