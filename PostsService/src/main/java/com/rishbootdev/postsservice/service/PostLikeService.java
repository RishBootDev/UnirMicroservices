package com.rishbootdev.postsservice.service;

import com.rishbootdev.postsservice.auth.UserContextHolder;
import com.rishbootdev.postsservice.entity.Post;
import com.rishbootdev.postsservice.entity.PostLike;
import com.rishbootdev.postsservice.event.PostLikedEvent;
import com.rishbootdev.postsservice.repository.PostLikeRepository;
import com.rishbootdev.postsservice.repository.PostsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostsRepository postsRepository;
    private final KafkaTemplate<Long, PostLikedEvent> kafkaTemplate;

    @Transactional
    public void likePost(Long postId) {
        Long userId = UserContextHolder.getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("Unauthorized");
        }

        Post post = postsRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        boolean alreadyLiked =
                postLikeRepository.existsByUserIdAndPostId(userId, postId);

        if (alreadyLiked) {
            throw new RuntimeException("Cannot like the same post again");
        }

        PostLike like = new PostLike();
        like.setUserId(userId);
        like.setPostId(postId);
        postLikeRepository.save(like);

        // denormalized counter
        post.setLikeCount(post.getLikeCount() + 1);

        kafkaTemplate.send(
                "post-liked-topic",
                postId,
                PostLikedEvent.builder()
                        .postId(postId)
                        .likedByUserId(userId)
                        .build()
        );
    }

    @Transactional
    public void unlikePost(Long postId) {
        Long userId = UserContextHolder.getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("Unauthorized");
        }

        Post post = postsRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        boolean alreadyLiked =
                postLikeRepository.existsByUserIdAndPostId(userId, postId);

        if (!alreadyLiked) {
            throw new RuntimeException("Cannot unlike a post you haven't liked");
        }

        postLikeRepository.deleteByUserIdAndPostId(userId, postId);

        // denormalized counter
        post.setLikeCount(Math.max(0, post.getLikeCount() - 1));

        log.info("Post {} unliked by user {}", postId, userId);
    }
}


