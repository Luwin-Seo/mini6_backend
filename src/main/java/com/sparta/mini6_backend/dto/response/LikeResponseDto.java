package com.sparta.mini6_backend.dto.response;

public class LikeResponseDto {
    private int like;
    private Long articleId;

    public LikeResponseDto(int like, Long articleId) {
        this.like = like;
        this.articleId = articleId;
    }
}
