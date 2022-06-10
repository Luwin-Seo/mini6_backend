package com.sparta.mini6_backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String username;
    private String password;
    private String profilePic;
}
