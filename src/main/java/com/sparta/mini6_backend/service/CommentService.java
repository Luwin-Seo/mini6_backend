package com.sparta.mini6_backend.service;

import com.sparta.mini6_backend.domain.Article;
import com.sparta.mini6_backend.domain.Comment;
import com.sparta.mini6_backend.dto.request.CommentRequestDto;
import com.sparta.mini6_backend.dto.response.CommentResponseDto;
import com.sparta.mini6_backend.repository.ArticleRepository;
import com.sparta.mini6_backend.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final ArticleRepository articleRepository;


    //댓글 생성
    @Transactional
    public Comment createComment(Long articleId, CommentRequestDto requestDto) {
        //게시글 조회 및 예외 발생
        //게시글을 알아야 코멘트가 어디 달릴지 알 수 있다.
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );

        //requestDto, article 합쳐서 엔티티 만들기
        Comment comment = new Comment(requestDto, article);

        //댓글 DB에 저장
        return commentRepository.save(comment);
    }

    /*
    수정 보류
     */
    public Comment updateComment(Long commentId, CommentRequestDto requestDto) {
        //댓글 조회 및 예외 발생
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다.")
        );

        //댓글 DB에 수정 반영
        comment.update(requestDto);

        return null;
    }

    //댓글 삭제
    public ResponseEntity<String> delete(Long commentId) {
        //댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다.")
        );

        //댓글 DB에서 삭제 - 반환 값이 없다.
        commentRepository.delete(target);

        return new ResponseEntity("댓글 삭제 성공", HttpStatus.OK);
    }
}
