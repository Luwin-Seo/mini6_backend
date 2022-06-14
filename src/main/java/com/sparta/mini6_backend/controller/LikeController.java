package com.sparta.mini6_backend.controller;

import com.sparta.mini6_backend.exceptionHandler.CustomException;
import com.sparta.mini6_backend.exceptionHandler.ErrorCode;
import com.sparta.mini6_backend.repository.LikeRepository;
import com.sparta.mini6_backend.security.UserDetailsImpl;
import com.sparta.mini6_backend.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    private final LikeRepository likeRepository;

    @PutMapping("/article/{articleId}/like")
    public int updateLike (@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long articleId) {
        if (userDetails == null){throw new CustomException(ErrorCode.AUTH_TOKEN_NOT_FOUND);}
        likeService.updateLike(articleId,userDetails.getUser().getUserId());
        return likeRepository.findAllByArticleId(articleId).size();
    }

    @GetMapping("/article/{articleId}/like")
    public int getLike(@PathVariable Long articleId) {
        return likeRepository.findAllByArticleId(articleId).size();
    }
}
