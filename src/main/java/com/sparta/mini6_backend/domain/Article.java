package com.sparta.mini6_backend.domain;

import com.sparta.mini6_backend.dto.request.ArticleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    @OneToMany(cascade = CascadeType.ALL)
    private List<Content> contents;

    @Column(nullable = false)
    private boolean done;

    public Article(Long userId, String username, String title, List<Content> contents, boolean done) {
        this.userId = userId;
        this.username = username;
        this.title = title;
        this.contents = contents;
        this.done = done;
    }

    public void updateArticle(ArticleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.done = requestDto.getDone();
    }
}
