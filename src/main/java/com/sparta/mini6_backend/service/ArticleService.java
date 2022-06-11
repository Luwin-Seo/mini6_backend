package com.sparta.mini6_backend.service;

import com.sparta.mini6_backend.domain.Article;
import com.sparta.mini6_backend.domain.Content;
import com.sparta.mini6_backend.dto.request.ArticleRequestDto;
import com.sparta.mini6_backend.repository.ArticleRepository;
import com.sparta.mini6_backend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    // 게시글 작성
    public Article createArticle(@AuthenticationPrincipal UserDetailsImpl userDetails, ArticleRequestDto requestDto) {
        Long userId = userDetails.getUser().getUserId();
        String username = userDetails.getUsername();
        String title = requestDto.getTitle();
        List<Content> contents = requestDto.getContents();
        Boolean done = requestDto.getDone();

        Article article = new Article(userId, username, title, contents, done);
        articleRepository.save(article);
        return article;
    }

    // 게시글 수정
    public Article updateArticle(Long articleId, ArticleRequestDto requestDto) {
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );

        article.updateArticle(requestDto);
        return article;
    }

    // 글 목록 조회
    public Page<Article> readArticles(int page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        Pageable pageable = PageRequest.of(page, 5, sort);
        return articleRepository.findAll(pageable);
    }
}
