package com.sparta.mini6_backend.service;

import com.sparta.mini6_backend.domain.Article;
import com.sparta.mini6_backend.domain.Comment;
import com.sparta.mini6_backend.domain.User;
import com.sparta.mini6_backend.dto.request.CommentRequestDto;
import com.sparta.mini6_backend.dto.response.CommentResponseDto;
import com.sparta.mini6_backend.exceptionHandler.CustomException;
import com.sparta.mini6_backend.exceptionHandler.ErrorCode;
import com.sparta.mini6_backend.repository.ArticleRepository;
import com.sparta.mini6_backend.repository.CommentRepository;
import com.sparta.mini6_backend.repository.UserRepository;
import com.sparta.mini6_backend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;


    //댓글 생성
    @Transactional
    public Comment createComment(Long articleId,
                                 CommentRequestDto requestDto,
                                 UserDetailsImpl userDetails) {
        //게시글 조회 및 예외 발생
        //게시글을 알아야 코멘트가 어디 달릴지 알 수 있다.
        Article article = articleRepository.findByArticleId(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND)
        );

        //comment 엔티티에 같이 넣어주기 위해서 로그인한 회원 찾기
        User joinUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)
                );

        //코멘트가 비어있을 때 예외 발생
        String commentStr = requestDto.getComment();
        if(commentStr.equals("")){
            throw new CustomException(ErrorCode.EMPTY_CONTENT);
        }

        //requestDto, article 합쳐서 comment 엔티티 만들기
        Comment comment = new Comment(requestDto, article, joinUser);

        //댓글 DB에 저장
        return commentRepository.save(comment);
    }

    //댓글 수정
    @Transactional
    public Comment updateComment(Long commentId,
                                 CommentRequestDto requestDto,
                                 UserDetails userDetails) {
        //댓글 조회 및 예외 발생
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        //작성자 본인이 맞는지 확인하기 위해서
        User joinUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND)
                );
        //본인 댓글인지 확인
        validateCheckUser(joinUser, comment);

        //코멘트가 비어있을 때 예외 발생
        String commentStr = requestDto.getComment();
        if(commentStr.equals("")){
            throw new CustomException(ErrorCode.EMPTY_CONTENT);
        }

        //댓글 DB에 수정 반영
        comment.update(requestDto);

        return comment;
    }

    //댓글 삭제
    @Transactional
    public ResponseEntity<String> delete(Long commentId,
                                         UserDetails userDetails) {
        //댓글 조회 및 예외 발생
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        //작성자 본인이 맞는지 확인하기 위해서
        User joinUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND)
                );
        //본인 댓글인지 확인
        validateCheckUser(joinUser, comment);

        //댓글 DB에서 삭제 - 반환 값이 없다.
        commentRepository.delete(comment);

        return new ResponseEntity("댓글 삭제 성공", HttpStatus.OK);
    }

    // 게시글 삭제 시 관련 댓글 모두 삭제
    @Transactional
    public void deleteComments(Long articleId) {
        commentRepository.deleteAllByArticleId(articleId);
    }

    private void validateCheckUser(User joinUser, Comment comment) {
        if (!joinUser.getUserId().equals(comment.getUserId())){
            throw new CustomException(ErrorCode.INVALID_AUTHORITY);
        }
    }

    public List<CommentResponseDto> getComment(Long articleId) {
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        List<Comment> comments = commentRepository.findByArticleIdOrderByCreatedAtAsc(articleId);
        for(int i = 0; i < comments.size(); i++) {
            User user = userRepository.findById(comments.get(i).getUserId()).orElseThrow(
                    ()-> new NullPointerException("사용자가 존재하지 않습니다")
            );
            CommentResponseDto commentResponseDto = new CommentResponseDto(comments.get(i), user.getProfilePic());
            commentResponseDtos.add(commentResponseDto);
        }
        return commentResponseDtos;
    }
}
