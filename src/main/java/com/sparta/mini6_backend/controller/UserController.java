package com.sparta.mini6_backend.controller;

import com.sparta.mini6_backend.dto.request.SignupRequestDto;
import com.sparta.mini6_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String userSignup (@Valid @RequestBody SignupRequestDto requestDto) {
     return userService.userSignup(requestDto);
    }

    @PostMapping("/dupcheck")
    public String dupCheck (String username) {
        return userService.dupCheck(username);
    }

    @GetMapping("/test")
    public String hello () {
        return "Hello World!";
    }

}
