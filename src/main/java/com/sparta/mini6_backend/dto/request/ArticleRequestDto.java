package com.sparta.mini6_backend.dto.request;

import lombok.Getter;

@Getter
public class ArticleRequestDto {
    private String title;
    private String content;
    private Boolean done;
    private String category;
}
