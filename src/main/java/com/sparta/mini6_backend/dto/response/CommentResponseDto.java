package com.sparta.mini6_backend.dto.response;

import com.sparta.mini6_backend.domain.Comment;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class CommentResponseDto {
    private String username;
    private String comment;
    private LocalDateTime createdAt;
    private String profilePic;
    private Long commentId;

    public CommentResponseDto(Comment comment, String profilePic) {
        this.username = comment.getUsername();
        this.comment = comment.getComment();
        this.createdAt = comment.getCreatedAt();
        this.profilePic = profilePic;
        this.commentId = comment.getCommentId();
    }
}
