package com.sparta.mini6_backend.service;

import com.sparta.mini6_backend.domain.Article;
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
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    // 게시글 작성
    public Article createArticle(@AuthenticationPrincipal UserDetailsImpl userDetails, ArticleRequestDto requestDto) {
        Long userId = userDetails.getUser().getUserId();
        String username = userDetails.getUsername();
        String title = requestDto.getTitle();
        String content = requestDto.getContent();
        Boolean done = requestDto.getDone();
        String category = requestDto.getCategory();

        Article article = new Article(userId, username, title, content, done, category);
        articleRepository.save(article);
        return article;
    }

    // 게시글 수정
    @Transactional
    public Article updateArticle(@AuthenticationPrincipal UserDetailsImpl userDetails, Long articleId, ArticleRequestDto requestDto) {
        Long loginId = userDetails.getUser().getUserId();
        Article article = articleRepository.findByArticleId(articleId).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );

        if (loginId != article.getUserId()) throw new IllegalArgumentException("로그인 정보가 일치하지 않습니다.");

        article.updateArticle(requestDto);
        articleRepository.save(article);
        return article;
    }

    // 게시글 삭제
    @Transactional
    public void deleteArticle(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long articleId) {
        Long loginId = userDetails.getUser().getUserId();
        Article article = articleRepository.findByArticleId(articleId).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );

        if (loginId != article.getUserId()) throw new IllegalArgumentException("로그인 정보가 일치하지 않습니다.");

        articleRepository.deleteByArticleId(articleId);
    }

    // 게시글 목록 조회
    public Page<Article> readArticles(int page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        Pageable pageable = PageRequest.of(page, 5, sort);
        return articleRepository.findAll(pageable);
    }
}
