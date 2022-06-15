package com.sparta.mini6_backend.service;

import com.sparta.mini6_backend.domain.Article;
import com.sparta.mini6_backend.domain.User;
import com.sparta.mini6_backend.dto.request.ArticleRequestDto;
import com.sparta.mini6_backend.dto.response.ArticleResponseDto;
import com.sparta.mini6_backend.exceptionHandler.CustomException;
import com.sparta.mini6_backend.exceptionHandler.ErrorCode;
import com.sparta.mini6_backend.repository.ArticleRepository;
import com.sparta.mini6_backend.repository.UserRepository;
import com.sparta.mini6_backend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    // 게시글 작성
    public ArticleResponseDto createArticle(@AuthenticationPrincipal UserDetailsImpl userDetails, ArticleRequestDto requestDto) {
        Long userId = userDetails.getUser().getUserId();
        String title = requestDto.getTitle();
        String content = requestDto.getContent();
        String category = requestDto.getCategory();

        if (title.equals("")) throw new CustomException(ErrorCode.EMPTY_CONTENT);
        if (content.equals("")) throw new CustomException(ErrorCode.EMPTY_CONTENT);
        if (category.equals("")) throw new CustomException(ErrorCode.EMPTY_CONTENT);

        Article article = new Article(userId, requestDto);
        articleRepository.save(article);

        User user = getUserDetails(article.getUserId());
        ArticleResponseDto responseDto = new ArticleResponseDto(article, user);
        return responseDto;
    }

    // 게시글 수정
    @Transactional
    public ArticleResponseDto updateArticle(@AuthenticationPrincipal UserDetailsImpl userDetails, Long articleId, ArticleRequestDto requestDto) {
        Long loginId = userDetails.getUser().getUserId();
        String title = requestDto.getTitle();
        String content = requestDto.getContent();
        String category = requestDto.getCategory();
        Article article = articleRepository.findByArticleId(articleId).orElseThrow(
                () -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND)
        );

        if (loginId != article.getUserId()) throw new IllegalArgumentException("로그인 정보가 일치하지 않습니다.");

        if (title.equals("")) throw new CustomException(ErrorCode.EMPTY_CONTENT);
        if (content.equals("")) throw new CustomException(ErrorCode.EMPTY_CONTENT);
        if (category.equals("")) throw new CustomException(ErrorCode.EMPTY_CONTENT);

        article.updateArticle(requestDto);
        articleRepository.save(article);

        User user = getUserDetails(article.getUserId());
        ArticleResponseDto responseDto = new ArticleResponseDto(article, user);
        return responseDto;
    }

    // 게시글 삭제
    @Transactional
    public void deleteArticle(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long articleId) {
        Long loginId = userDetails.getUser().getUserId();
        Article article = articleRepository.findByArticleId(articleId).orElseThrow(
                () -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND)
        );

        if (loginId != article.getUserId()) throw new CustomException(ErrorCode.INVALID_AUTHORITY);

        articleRepository.deleteByArticleId(articleId);
    }

    // 게시글 목록 조회
//    public Page<Article> readArticles(int page) {
//        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
//        Pageable pageable = PageRequest.of(page, 5, sort);
//        return articleRepository.findAll(pageable);
//    }
    public List<ArticleResponseDto> readArticles() {
        List<ArticleResponseDto> responseList = new ArrayList<>();
        List<Article> articleList = articleRepository.findAll();

        for(int i = 0; i < articleList.size(); i++) {
            User user = getUserDetails(articleList.get(i).getUserId());
            ArticleResponseDto responseDto = new ArticleResponseDto(articleList.get(i), user);
            responseList.add(responseDto);
        }
        return responseList;
    }

    // 게시글 카테고리별 목록 조회
    public List<ArticleResponseDto> readArticlesByCategory(String category) {
        List<ArticleResponseDto> responseList = new ArrayList<>();
        List<Article> articleList = articleRepository.findAllByCategory(category);

        for(int i = 0; i < articleList.size(); i++) {
            User user = getUserDetails(articleList.get(i).getUserId());
            ArticleResponseDto responseDto = new ArticleResponseDto(articleList.get(i), user);
            responseList.add(responseDto);
        }
        return responseList;
    }

    // 게시글 상세 조회
    public ArticleResponseDto readArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND)
        );

        User user = getUserDetails(article.getUserId());
        ArticleResponseDto responseDto = new ArticleResponseDto(article, user);
        return responseDto;
    }

    // 게시글 완료 처리
    @Transactional
    public ArticleResponseDto doneArticle(@AuthenticationPrincipal UserDetailsImpl userDetails, Long articleId) {
        Long loginId = userDetails.getUser().getUserId();
        Article article = articleRepository.findByArticleId(articleId).orElseThrow(
                () -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND)
        );

        if (loginId != article.getUserId()) throw new CustomException(ErrorCode.INVALID_AUTHORITY);

        article.doneArticle();
        articleRepository.save(article);

        User user = getUserDetails(article.getUserId());
        ArticleResponseDto responseDto = new ArticleResponseDto(article, user);
        return responseDto;
    }

    // 게시글 해결분 모아보기
    public List<ArticleResponseDto> readSolvedArticles() {
        List<ArticleResponseDto> responseList = new ArrayList<>();
        List<Article> articleList = articleRepository.findAllByDone(true);

        for(int i = 0; i < articleList.size(); i++) {
            User user = getUserDetails(articleList.get(i).getUserId());
            ArticleResponseDto responseDto = new ArticleResponseDto(articleList.get(i), user);
            responseList.add(responseDto);
        }
        return responseList;
    }

    // 게시글 미해결분 모아보기
    public List<ArticleResponseDto> readUnsolvedArticles() {
        List<ArticleResponseDto> responseList = new ArrayList<>();
        List<Article> articleList = articleRepository.findAllByDone(false);

        for(int i = 0; i < articleList.size(); i++) {
            User user = getUserDetails(articleList.get(i).getUserId());
            ArticleResponseDto responseDto = new ArticleResponseDto(articleList.get(i), user);
            responseList.add(responseDto);
        }
        return responseList;
    }

    // responseDto에 넣을 User 정보 불러오기
    private User getUserDetails(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        return user;
    }
}
