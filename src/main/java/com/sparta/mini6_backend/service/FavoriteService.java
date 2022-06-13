package com.sparta.mini6_backend.service;

import com.sparta.mini6_backend.domain.Article;
import com.sparta.mini6_backend.domain.Favorite;
import com.sparta.mini6_backend.repository.ArticleRepository;
import com.sparta.mini6_backend.repository.FavoriteRepository;
import com.sparta.mini6_backend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final ArticleRepository articleRepository;
    private final FavoriteRepository favoriteRepository;

    @Transactional
    public void setFavorite(@AuthenticationPrincipal UserDetailsImpl userDetails, Long articleId) {
        Long userId = userDetails.getUser().getUserId();

        if (favoriteRepository.findByUserIdAndArticleId(userId, articleId).isPresent()) {
            Favorite favorite = favoriteRepository.findByUserIdAndArticleId(userId, articleId).orElseThrow(
                    () -> new NullPointerException("즐겨찾기가 존재하지 않습니다."));
            favoriteRepository.delete(favorite);
        } else {
            Favorite favorite = new Favorite(userId, articleId);
            favoriteRepository.save(favorite);
        }
    }

    public List<Article> getFavorites(UserDetailsImpl userDetails, Long articleId) {
        Long userId = userDetails.getUser().getUserId();

        List<Favorite> favoriteList = favoriteRepository.findAllByUserId(userId);
        List<Article> favoriteArticles = new ArrayList<>();

        for(int i = 0; i < favoriteList.size(); i++) {
            favoriteList.get(i).getArticleId();
            Article article = articleRepository.findById(articleId).orElseThrow(
                    () -> new NullPointerException("게시글이 존재하지 않습니다.")
            );
            favoriteArticles.add(article);
        }

        return favoriteArticles;
    }
}
