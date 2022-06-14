package com.sparta.mini6_backend.service;

import com.sparta.mini6_backend.domain.Like;
import com.sparta.mini6_backend.exceptionHandler.CustomException;
import com.sparta.mini6_backend.exceptionHandler.ErrorCode;
import com.sparta.mini6_backend.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    @Transactional
    public void updateLike(Long articleId, Long userId) {
        if(likeRepository.findByArticleIdAndUserId(articleId, userId).isPresent()) {
            Like like = likeRepository.findByArticleIdAndUserId(articleId, userId).orElseThrow(
                    () -> new CustomException(ErrorCode.LIKE_NOT_FOUND));
            likeRepository.delete(like);
        } else {
            Like like = new Like(userId,articleId);
            likeRepository.save(like);
        }
    }
}
