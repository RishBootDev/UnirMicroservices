package com.rishbootdev.postsservice.repository;

import com.rishbootdev.postsservice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
  //  List<Post> getPostsBy(String field);
}
