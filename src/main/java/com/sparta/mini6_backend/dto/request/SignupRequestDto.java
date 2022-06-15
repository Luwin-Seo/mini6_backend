package com.sparta.mini6_backend.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignupRequestDto {

    @NotBlank
    @Pattern(regexp = "(?=.*[\\da-zA-Z])[가-힣a-zA-Z\\d-_.]{4,15}")
    private String username;

    @NotBlank
    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{4,20}")
    private String password;

    @NotBlank
    private String profilePic;
}
