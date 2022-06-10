package com.sparta.mini6_backend.controller;

import com.sparta.mini6_backend.dto.request.SignupRequestDto;
import com.sparta.mini6_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String userSignup (@RequestBody SignupRequestDto requestDto) {
     return userService.userSignup(requestDto);
    }

    @PostMapping("/dupcheck")
    public String dupCheck (String username) {
        return userService.dupCheck(username);
    }
}
