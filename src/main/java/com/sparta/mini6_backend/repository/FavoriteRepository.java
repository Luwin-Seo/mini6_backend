package com.sparta.mini6_backend.repository;

import com.sparta.mini6_backend.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserIdAndArticleId(Long userId, Long articleId);

    List<Favorite> findAllByUserId(Long userId);
}
