package com.sparta.mini6_backend.repository;

import com.sparta.mini6_backend.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByArticleId(Long ArticleId);
}
