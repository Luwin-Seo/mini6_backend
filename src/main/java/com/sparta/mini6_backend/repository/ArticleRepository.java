package com.sparta.mini6_backend.repository;

import com.sparta.mini6_backend.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAll(Pageable pageable);

    Optional<Article> findByArticleId(Long articleId);

    void deleteByArticleId(Long articleId);
}
