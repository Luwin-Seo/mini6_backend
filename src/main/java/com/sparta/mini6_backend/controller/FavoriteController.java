package com.sparta.mini6_backend.controller;

import com.sparta.mini6_backend.dto.response.ArticleResponseDto;
import com.sparta.mini6_backend.exceptionHandler.CustomException;
import com.sparta.mini6_backend.exceptionHandler.ErrorCode;
import com.sparta.mini6_backend.security.UserDetailsImpl;
import com.sparta.mini6_backend.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    //게시글 즐겨찾기 추가
    @PutMapping("/api/articles/{articleId}/favorite")
    public ArticleResponseDto setFavorite(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long articleId) {
        if (userDetails != null) {
            return favoriteService.setFavorite(userDetails, articleId);
        }
        throw new CustomException(ErrorCode.AUTH_TOKEN_NOT_FOUND);
    }

    //게시글 즐겨찾기 모아보기
    @GetMapping("/api/articles/favorites")
    public List<ArticleResponseDto> getFavorites(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return favoriteService.getFavorites(userDetails);
    }
}
