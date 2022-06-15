package com.sparta.mini6_backend.controller;

import com.sparta.mini6_backend.dto.request.ArticleRequestDto;
import com.sparta.mini6_backend.dto.response.ArticleResponseDto;
import com.sparta.mini6_backend.exceptionHandler.CustomException;
import com.sparta.mini6_backend.exceptionHandler.ErrorCode;
import com.sparta.mini6_backend.security.UserDetailsImpl;
import com.sparta.mini6_backend.service.ArticleService;
import com.sparta.mini6_backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;

    // 게시글 작성
    @PostMapping("/api/article")
    public ArticleResponseDto createArticle(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ArticleRequestDto requestDto) {
        if (userDetails != null) {
            ArticleResponseDto responseDto = articleService.createArticle(userDetails, requestDto);
            return responseDto;
        }
        throw new CustomException(ErrorCode.AUTH_TOKEN_NOT_FOUND);
    }

    // 게시글 수정
    @PutMapping("/api/articles/{articleId}")
    public ArticleResponseDto updateArticle(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long articleId, @RequestBody ArticleRequestDto requestDto) {
        if (userDetails != null) {
            ArticleResponseDto responseDto = articleService.updateArticle(userDetails, articleId, requestDto);
            return responseDto;
        }
        throw new CustomException(ErrorCode.AUTH_TOKEN_NOT_FOUND);
    }

    // 게시글 삭제
    @DeleteMapping("/api/articles/{articleId}")
    public String deleteArticle(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long articleId) {
        if (userDetails != null) {
            articleService.deleteArticle(userDetails, articleId);
            commentService.deleteComments(articleId);
            return "게시글이 삭제되었습니다.";
        }
        throw new CustomException(ErrorCode.AUTH_TOKEN_NOT_FOUND);
    }

    // 게시글 목록 조회
//    @GetMapping("/api/articles/page/{page}")
//    public Page<Article> readArticles(@PathVariable int page) {
//        page -= 1;
//        Page<Article> articles = articleService.readArticles(page);
//        return articles;
//    }
    @GetMapping("/api/articles")
    public List<ArticleResponseDto> readArticles() {
        return articleService.readArticles();
    }

    // 게시글 카테고리별 목록 조회
    @GetMapping("/api/articles/category/{category}")
    public List<ArticleResponseDto> readArticlesByCategory(@PathVariable String category) {
        return articleService.readArticlesByCategory(category);
    }

    // 게시글 상세 조회
    @GetMapping("/api/articles/{articleId}")
    public ArticleResponseDto readArticle(@PathVariable Long articleId) {
        return articleService.readArticle(articleId);
    }

    // 게시글 완료 처리
    @PatchMapping("/api/articles/{articleId}/done")
    public ArticleResponseDto doneArticle(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long articleId) {
        if (userDetails != null) {
            ArticleResponseDto responseDto = articleService.doneArticle(userDetails, articleId);
            return responseDto;
        }
        throw new CustomException(ErrorCode.AUTH_TOKEN_NOT_FOUND);
    }

    // 게시글 해결분 모아보기
    @GetMapping("/api/articles/solved")
    public List<ArticleResponseDto> readSolvedArticles() {
        return articleService.readSolvedArticles();
    }

    // 게시글 미해결분 모아보기
    @GetMapping("/api/articles/unsolved")
    public List<ArticleResponseDto> readUnsolvedArticles() {
        return articleService.readUnsolvedArticles();
    }
}
