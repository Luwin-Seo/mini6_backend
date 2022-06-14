package com.sparta.mini6_backend.service;

import com.sparta.mini6_backend.domain.Article;
import com.sparta.mini6_backend.domain.User;
import com.sparta.mini6_backend.dto.request.ArticleRequestDto;
import com.sparta.mini6_backend.dto.response.ArticleResponseDto;
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

        if (title.equals("")) throw new IllegalArgumentException("제목을 입력하세요.");
        if (content.equals("")) throw new IllegalArgumentException("내용을 입력하세요.");
        if (category.equals("")) throw new IllegalArgumentException("카테고리를 선택하세요");

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
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );

        if (loginId != article.getUserId()) throw new IllegalArgumentException("로그인 정보가 일치하지 않습니다.");

        if (title.equals("")) throw new IllegalArgumentException("제목을 입력하세요.");
        if (content.equals("")) throw new IllegalArgumentException("내용을 입력하세요.");
        if (category.equals("")) throw new IllegalArgumentException("카테고리를 선택하세요");

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
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );

        if (loginId != article.getUserId()) throw new IllegalArgumentException("로그인 정보가 일치하지 않습니다.");

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
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
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
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );

        if (loginId != article.getUserId()) throw new IllegalArgumentException("로그인 정보가 일치하지 않습니다.");

        article.doneArticle();
        articleRepository.save(article);

        User user = getUserDetails(article.getUserId());
        ArticleResponseDto responseDto = new ArticleResponseDto(article, user);
        return responseDto;
    }

    // responseDto에 넣을 User 정보 불러오기
    private User getUserDetails(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("User가 존재하지 않습니다.")
        );
        return user;
    }
}
