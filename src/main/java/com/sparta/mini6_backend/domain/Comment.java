package com.sparta.mini6_backend.domain;

import com.sparta.mini6_backend.dto.request.CommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    //댓글에 부모 게시글 있어야 한다.
    //여러개의 Comment가 하나의 Article에 달린다.
    @ManyToOne //@ManyToOne 다대일 관계 설정 - 해당 댓글 엔티티 여러개가, 하나의 Article에 연관된다.
    @JoinColumn(name="articleId") //연결된 대상의 정보를 가져와서 articleId에 넣겠다. - "article_id" / 컬럼에 Article의 대표값을 저장!!
    private Article article;

    @ManyToOne //@ManyToOne 다대일 관계 설정 - 해당 댓글 엔티티 여러개가, 하나의 User에 연관된다.
    @JoinColumn(name="userId") //연결된 대상의 정보를 가져와서 userId에 넣겠다. - "userId" / 컬럼에 User의 대표값을 저장!!
    private User user;

    @Column(nullable = false) //username은 나중에 user 테이블에서 가져올 수 있는지 확인하기
    private String username;

    @Column(nullable = false)
    private String comment;

    public Comment(CommentRequestDto requestDto, Article article) {
        this.comment = requestDto.getComment();
        this.article = article;
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}
