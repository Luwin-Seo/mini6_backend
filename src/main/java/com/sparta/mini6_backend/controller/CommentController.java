package com.sparta.mini6_backend.controller;

import com.sparta.mini6_backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 조회
    @GetMapping("/api/comment/{articleId}")
    public
}
