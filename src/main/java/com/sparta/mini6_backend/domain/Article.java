package com.sparta.mini6_backend.domain;

import com.sparta.mini6_backend.dto.request.ArticleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Article extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean done;

    @Column(nullable = false)
    private String category;

    public Article(Long userId, String username, ArticleRequestDto requestDto) {
        this.userId = userId;
        this.username = username;
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.done = requestDto.getDone();
        this.category = requestDto.getCategory();
    }

    public void updateArticle(ArticleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.category = requestDto.getCategory();
    }

    public void doneArticle() {
        this.done = true;
    }
}
