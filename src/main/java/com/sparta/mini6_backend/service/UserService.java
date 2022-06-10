package com.sparta.mini6_backend.service;

import com.sparta.mini6_backend.domain.User;
import com.sparta.mini6_backend.dto.request.SignupRequestDto;
import com.sparta.mini6_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String userSignup(SignupRequestDto requestDto) {
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("중복된 사용자명");
        }
        userRepository.save(new User(requestDto));
        return "회원가입이 정상처리 되었습니다";
    }
}
