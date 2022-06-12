package com.sparta.mini6_backend.repository;

import com.sparta.mini6_backend.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByArticleIdAndUserId(Long articleId, Long userId);
    List<Like> findAllByArticleIdAndUserId(Long articleId, Long userId);
}
