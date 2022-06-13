package com.sparta.mini6_backend.controller;

import com.sparta.mini6_backend.domain.Article;
import com.sparta.mini6_backend.dto.request.ArticleRequestDto;
import com.sparta.mini6_backend.repository.ArticleRepository;
import com.sparta.mini6_backend.security.UserDetailsImpl;
import com.sparta.mini6_backend.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final ArticleService articleService;

    // 게시글 작성
    @PostMapping("/api/article")
    public Article createArticle(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ArticleRequestDto requestDto) {
        if (userDetails != null) {
            Article article = articleService.createArticle(userDetails, requestDto);
            return article;
        }
        throw new NullPointerException("로그인이 필요합니다.");
    }

    // 게시글 수정
    @PutMapping("/api/articles/{articleId}")
    public Article updateArticle(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long articleId, @RequestBody ArticleRequestDto requestDto) {
        if (userDetails != null) {
            Article article = articleService.updateArticle(userDetails, articleId, requestDto);
            return article;
        }
        throw new NullPointerException("로그인이 필요합니다.");
    }

    // 게시글 삭제
    @DeleteMapping("/api/articles/{articleId}")
    public String deleteArticle(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long articleId) {
        if (userDetails != null) {
            articleService.deleteArticle(userDetails, articleId);
            return "게시글이 삭제되었습니다.";
        }
        throw new NullPointerException("로그인이 필요합니다.");
    }

    // 게시글 목록 조회
//    @GetMapping("/api/articles/page/{page}")
//    public Page<Article> readArticles(@PathVariable int page) {
//        page -= 1;
//        Page<Article> articles = articleService.readArticles(page);
//        return articles;
//    }
    @GetMapping("/api/articles")
    public List<Article> readArticles() {
        return articleRepository.findAll();
    }

    // 게시글 상세 조회
    @GetMapping("/api/articles/{articleId}")
    public Article readArticle(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long articleId, HttpServletResponse response) throws IOException {
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );

        String profilePic = userDetails.getUser().getProfilePic();
        response.addHeader("ProfilePic", profilePic);

        return article;
    }

    // 게시글 완료 처리
    @PatchMapping("/api/articles/{articleId}/done")
    public Article doneArticle(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long articleId) {
        if (userDetails != null) {
            Article article = articleService.doneArticle(userDetails, articleId);
            return article;
        }
        throw new NullPointerException("로그인이 필요합니다.");
    }
}
