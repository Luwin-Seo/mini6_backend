package com.sparta.mini6_backend.dto.response;

import com.sparta.mini6_backend.domain.Article;
import com.sparta.mini6_backend.domain.User;
import com.sparta.mini6_backend.repository.UserRepository;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleResponseDto {

    private UserRepository userRepository;
    private LocalDateTime createdAt;
    private Long articleId;
    private Long userId;
    private String username;
    private String profilePic;
    private String title;
    private String content;
    private Boolean done;
    private String category;

    public ArticleResponseDto(Article article) {
        this.createdAt = article.getCreatedAt();
        this.articleId = article.getArticleId();
        this.userId = article.getUserId();
        User user = userRepository.findById(this.userId).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다.")
        );
        this.username = user.getUsername();
        this.profilePic = user.getProfilePic();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.done = article.getDone();
        this.category = article.getCategory();
    }
}
