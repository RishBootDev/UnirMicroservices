package com.rishbootdev.postsservice.repository;

import com.rishbootdev.postsservice.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

        boolean existsByUserIdAndPostId(Long userId, Long postId);

        @Transactional
        void deleteByUserIdAndPostId(Long userId, Long postId);

        List<PostLike> findByUserIdAndPostIdIn(Long userId, List<Long> postIds);

        void deleteAllByPostId(Long postId);

}
