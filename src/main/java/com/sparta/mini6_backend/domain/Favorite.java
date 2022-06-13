package com.sparta.mini6_backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteId;

    @Column
    private Long userId;

    @Column
    private Long articleId;

    public Favorite(Long userId, Long articleId) {
        this.userId = userId;
        this.articleId = articleId;
    }
}
