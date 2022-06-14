package com.sparta.mini6_backend.service;

import com.sparta.mini6_backend.domain.Article;
import com.sparta.mini6_backend.domain.Favorite;
import com.sparta.mini6_backend.domain.User;
import com.sparta.mini6_backend.dto.response.ArticleResponseDto;
import com.sparta.mini6_backend.repository.ArticleRepository;
import com.sparta.mini6_backend.repository.FavoriteRepository;
import com.sparta.mini6_backend.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Transactional
    public ArticleResponseDto setFavorite(@AuthenticationPrincipal UserDetailsImpl userDetails, Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );

        Long userId = userDetails.getUser().getUserId();

        if (favoriteRepository.findByUserIdAndArticleId(userId, articleId).isPresent()) {
            Favorite favorite = favoriteRepository.findByUserIdAndArticleId(userId, articleId).orElseThrow(
                    () -> new NullPointerException("즐겨찾기가 존재하지 않습니다."));
            favoriteRepository.delete(favorite);
        } else {
            Favorite favorite = new Favorite(userId, articleId);
            favoriteRepository.save(favorite);
        }

        User user = getUserDetails(article.getUserId());
        ArticleResponseDto responseDto = new ArticleResponseDto(article, user);
        return responseDto;
    }

    public List<ArticleResponseDto> getFavorites(UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getUserId();

        List<Favorite> favoriteList = favoriteRepository.findAllByUserId(userId);
        List<ArticleResponseDto> favoriteArticles = new ArrayList<>();

        for(int i = 0; i < favoriteList.size(); i++) {
            Long articleId = favoriteList.get(i).getArticleId();
            Article article = articleRepository.findById(articleId).orElseThrow(
                    () -> new NullPointerException("게시글이 존재하지 않습니다.")
            );

            User user = getUserDetails(article.getUserId());
            ArticleResponseDto responseDto = new ArticleResponseDto(article, user);
            favoriteArticles.add(responseDto);
        }

        return favoriteArticles;
    }

    // responseDto에 넣을 User 정보 불러오기
    private User getUserDetails(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("User가 존재하지 않습니다.")
        );
        return user;
    }
}
