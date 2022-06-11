package com.sparta.mini6_backend.repository;

import com.sparta.mini6_backend.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAllPageable(Pageable pageable);
}
