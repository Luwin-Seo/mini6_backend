package com.sparta.mini6_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentResponseDto {
    private Long articleId;
    private String username;
    private String comment;
    private String createdAt;
}
