package com.sparta.mini6_backend.service;

import com.sparta.mini6_backend.domain.Like;
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
                    () -> new IllegalArgumentException("존재하지 않는 좋아요"));
            likeRepository.delete(like);
        } else {
            Like like = new Like(userId,articleId);
            likeRepository.save(like);
        }
    }
}
