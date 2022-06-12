package com.sparta.mini6_backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @Column
    private Long userId;

    @Column
    private Long articleId;

    public Like(Long userId, Long articleId) {
        this.userId = userId;
        this.articleId = articleId;
    }
}
