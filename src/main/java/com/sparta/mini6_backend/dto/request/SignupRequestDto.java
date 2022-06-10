package com.sparta.mini6_backend.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignupRequestDto {

    @NotBlank
    @Pattern(regexp = "(?=.*[0-9a-zA-Z]).{4,20}")
    private String username;

    @NotBlank
    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{4,20}")
    private String password;

    @NotBlank
    private String profilePic;
}
