package com.sparta.mini6_backend.controller;

import com.sparta.mini6_backend.exceptionHandler.CustomException;
import com.sparta.mini6_backend.exceptionHandler.ErrorCode;
import com.sparta.mini6_backend.repository.LikeRepository;
import com.sparta.mini6_backend.security.UserDetailsImpl;
import com.sparta.mini6_backend.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    private final LikeRepository likeRepository;

    @PutMapping("/api/articles/{articleId}/like")
    public int updateLike (@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long articleId) {
        if (userDetails == null){throw new CustomException(ErrorCode.AUTH_TOKEN_NOT_FOUND);}
        likeService.updateLike(articleId,userDetails.getUser().getUserId());
        return likeRepository.findAllByArticleId(articleId).size();
    }

    @GetMapping("/api/articles/{articleId}/like")
    public int getLike(@PathVariable Long articleId) {
        return likeRepository.findAllByArticleId(articleId).size();
    }
}
