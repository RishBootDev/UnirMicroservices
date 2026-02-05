package com.rishbootdev.postsservice.repository;

import com.rishbootdev.postsservice.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    List<PostComment> findByPostId(Long postId);

    void deleteAllByPostId(Long postId);
}

