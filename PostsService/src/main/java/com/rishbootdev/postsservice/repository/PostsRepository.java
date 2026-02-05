package com.rishbootdev.postsservice.repository;

import com.rishbootdev.postsservice.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostsRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);

    Page<Post> findByUserIdInOrderByCreatedAtDesc(
            List<Long> userIds,
            Pageable pageable
    );

    Page<Post> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
