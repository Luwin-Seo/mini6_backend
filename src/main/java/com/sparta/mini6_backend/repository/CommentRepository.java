package com.sparta.mini6_backend.repository;

import com.sparta.mini6_backend.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByArticleIdOrderByCreatedAtDesc(Long articleId);
    //findByArticleId By OrderBy CreatedAtDesc 수정시간된 날짜를 기준으로 내림차순으로 정렬해줘(최신순으로 정렬해줘)
    //최신순으로 정렬하는 SQL문을 JPA가 다 알아서 짜준다.
}
