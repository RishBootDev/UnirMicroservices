package com.rishbootdev.postsservice.service;

import com.rishbootdev.postsservice.auth.UserContextHolder;
import com.rishbootdev.postsservice.dto.PostCreateRequestDto;
import com.rishbootdev.postsservice.dto.PostDto;
import com.rishbootdev.postsservice.entity.Post;
import com.rishbootdev.postsservice.enums.PostType;
import com.rishbootdev.postsservice.enums.PostVisibility;
import com.rishbootdev.postsservice.event.PostCreatedEvent;
import com.rishbootdev.postsservice.event.PostDeletedEvent;
import com.rishbootdev.postsservice.repository.PostCommentRepository;
import com.rishbootdev.postsservice.repository.PostLikeRepository;
import com.rishbootdev.postsservice.repository.PostsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService {

    private final PostsRepository postsRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostCommentRepository postCommentRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<Long, PostCreatedEvent> kafkaTemplate;
    private final KafkaTemplate<Long, PostDeletedEvent> postDeletedKafka;

    public PostDto createPost(PostCreateRequestDto dto) {
        Long userId = UserContextHolder.getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("Unauthorized");
        }

        Post post = modelMapper.map(dto, Post.class);
        post.setUserId(userId);

        if (post.getType() == null) post.setType(PostType.NORMAL);
        if (post.getVisibility() == null) post.setVisibility(PostVisibility.PUBLIC);

        Post saved = postsRepository.save(post);

        kafkaTemplate.send(
                "post-created-topic",
                saved.getId(),
                PostCreatedEvent.builder()
                        .postId(saved.getId())
                        .creatorId(userId)
                        .build()
        );

        return modelMapper.map(saved, PostDto.class);
    }

    public PostDto getPost(Long id) {
        Post post = postsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return modelMapper.map(post, PostDto.class);
    }

    @Transactional
    public void deletePost(Long postId) {
        Long userId = UserContextHolder.getCurrentUserId();
        if (userId == null) throw new RuntimeException("Unauthorized");

        Post post = postsRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUserId().equals(userId)) {
            throw new RuntimeException("You can delete only your own post");
        }


        postLikeRepository.deleteAllByPostId(postId);
        postCommentRepository.deleteAllByPostId(postId);

        post.setDeleted(true);
        post.setContent("[deleted]");
        post.setMediaUrl(null);
        post.setLikeCount(0);
        post.setCommentCount(0);

        log.info("Post {} and all likes/comments deleted", postId);

        postDeletedKafka.send(
                "post-deleted-topic",
                postId,
                PostDeletedEvent.builder()
                        .postId(postId)
                        .deletedByUserId(userId)
                        .build()
        );
    }
}
