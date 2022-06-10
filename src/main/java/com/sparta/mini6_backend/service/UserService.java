package com.sparta.mini6_backend.service;

import com.sparta.mini6_backend.domain.User;
import com.sparta.mini6_backend.dto.request.SignupRequestDto;
import com.sparta.mini6_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public String userSignup(SignupRequestDto requestDto) {
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("중복된 사용자명");
        }
        User user = new User(requestDto);
        String password = passwordEncoder.encode(requestDto.getPassword());
        user.setPassword(password);
        userRepository.save(user);
        return "회원가입이 정상처리 되었습니다";
    }

    public String dupCheck (String username) {
        return (!userRepository.findByUsername(username).isPresent()) ?
                "사용 가능한 아이디입니다" :
                username + "은 이미 존재하는 아이디입니다";
    }
}
