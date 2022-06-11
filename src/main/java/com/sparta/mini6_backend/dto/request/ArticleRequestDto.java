package com.sparta.mini6_backend.dto.request;

import com.sparta.mini6_backend.domain.Content;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticleRequestDto {
    private String title;
    private List<Content> contents;
    private Boolean done;
}
