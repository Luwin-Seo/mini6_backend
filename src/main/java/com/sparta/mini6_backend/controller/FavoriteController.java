package com.sparta.mini6_backend.controller;

import com.sparta.mini6_backend.domain.Article;
import com.sparta.mini6_backend.repository.ArticleRepository;
import com.sparta.mini6_backend.repository.FavoriteRepository;
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

    private final ArticleRepository articleRepository;
    private final FavoriteRepository favoriteRepository;
    private final FavoriteService favoriteService;

    //게시글 즐겨찾기 추가
    @PutMapping("/api/articles/{articleId}/favorite")
    public Article setFavorite(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long articleId) {
        if (userDetails != null) {
            favoriteService.setFavorite(userDetails, articleId);
            Article article = articleRepository.findById(articleId).orElseThrow(
                    () -> new NullPointerException("게시글이 존재하지 않습니다.")
            );
            return article;
        }
        throw new NullPointerException("로그인이 필요합니다.");
    }

    //게시글 즐겨찾기 모아보기
    @GetMapping("/api/articles/{articleId}/favorite")
    public List<Article> getFavorites(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long articleId) {
        return favoriteService.getFavorites(userDetails, articleId);
    }
}
