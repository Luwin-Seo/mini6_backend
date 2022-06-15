package com.sparta.mini6_backend.dto.response;

import com.sparta.mini6_backend.domain.Article;
import com.sparta.mini6_backend.domain.User;

import java.time.LocalDateTime;

public class ArticleResponseDtoForFav {
    private LocalDateTime createdAt;
    private Long articleId;
    private Long userId;
    private String username;
    private String profilePic;
    private String title;
    private String content;
    private Boolean done;
    private String category;

    private boolean fav;

    public ArticleResponseDtoForFav(Article article, User user) {
        this.createdAt = article.getCreatedAt();
        this.articleId = article.getArticleId();
        this.userId = article.getUserId();
        this.username = user.getUsername();
        this.profilePic = user.getProfilePic();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.done = article.getDone();
        this.category = article.getCategory();
        this.fav = true;
    }
}

