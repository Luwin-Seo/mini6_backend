package com.sparta.mini6_backend.controller;


import com.sparta.mini6_backend.domain.Comment;
import com.sparta.mini6_backend.dto.request.CommentRequestDto;
import com.sparta.mini6_backend.repository.CommentRepository;
import com.sparta.mini6_backend.security.UserDetailsImpl;
import com.sparta.mini6_backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final CommentRepository commentRepository;

    //댓글 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public List<Comment> getComments(@PathVariable Long articleId) {

        //댓글 ArticleId로 조회하고 내림차순으로 정렬
        return commentRepository.findByArticleIdByOrderByCreatedAtDesc(articleId);
    }

    //댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")
    public Comment createComment(@PathVariable Long articleId,
                                 @RequestBody CommentRequestDto requestDto,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        //로그인된 사용자 확인 - 다시 확인하기
        if(userDetails.getUsername() == null )
            throw new IllegalArgumentException("로그인 후 사용해주세요");

        //서비스에게 위임
        //받은 값을 반환
        return commentService.createComment(articleId, requestDto, userDetails);
    }

    //댓글 수정
    @PutMapping("/api/articles/comments/{commentId}")
    public Comment updateComment(@PathVariable Long commentId,
                                 @RequestBody CommentRequestDto requestDto,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails){
        //서비스에 위임
        return commentService.updateComment(commentId, requestDto, userDetails);

    }

    //댓글 삭제
    @DeleteMapping("/api/articles/comments/{commentId}")
    public ResponseEntity delete(@PathVariable Long commentId,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {

        //서비스에 위임
        return commentService.delete(commentId, userDetails);
    }
}
