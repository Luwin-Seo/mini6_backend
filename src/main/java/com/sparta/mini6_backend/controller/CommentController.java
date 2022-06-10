package com.sparta.mini6_backend.controller;


import com.sparta.mini6_backend.domain.Comment;
import com.sparta.mini6_backend.dto.request.CommentRequestDto;
import com.sparta.mini6_backend.repository.CommentRepository;
import com.sparta.mini6_backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final CommentRepository commentRepository;

    //댓글 조회
    @GetMapping("/api/comment/{articleId}")
    public List<Comment> getComments(@PathVariable Long articleId) {

        return commentRepository.findByArticleId(articleId);
    }

    //댓글 생성
    @PostMapping("/api/comment/{articleId}")
    public Comment createComment(@PathVariable Long articleId,
                                 @RequestBody CommentRequestDto requestDto) {
        //서비스에게 위임
        //받은 값을 반환
        return commentService.createComment(articleId, requestDto);
    }

    //댓글 수정
    @PutMapping("/api/comment/{commentId}")
    public Comment updateComment(@PathVariable Long commentId,
                                 @RequestBody CommentRequestDto requestDto){
        //서비스에 위임
        commentService.updateComment(commentId, requestDto);

        return null;
    }

    @DeleteMapping("/api/comment/{commentId}")
    public ResponseEntity delete(@PathVariable Long commentId) {

        //서비스에 위임
        return commentService.delete(commentId);
    }
}
