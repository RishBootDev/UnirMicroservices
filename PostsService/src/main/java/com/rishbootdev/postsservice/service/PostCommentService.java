package com.rishbootdev.postsservice.service;


import com.rishbootdev.postsservice.auth.UserContextHolder;
import com.rishbootdev.postsservice.dto.CommentCreateDto;
import com.rishbootdev.postsservice.entity.Post;
import com.rishbootdev.postsservice.entity.PostComment;
import com.rishbootdev.postsservice.event.PostCommentedEvent;
import com.rishbootdev.postsservice.repository.PostCommentRepository;
import com.rishbootdev.postsservice.repository.PostsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostCommentService {

    private final PostCommentRepository commentRepository;
    private final PostsRepository postsRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<Long, PostCommentedEvent> kafkaTemplate;
    private final com.rishbootdev.postsservice.clients.ProfileClient profileClient;

    @Transactional
    public void addComment(Long postId, CommentCreateDto dto) {
        Long userId = UserContextHolder.getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("Unauthorized");
        }

        PostComment comment = modelMapper.map(dto, PostComment.class);
        comment.setPostId(postId);
        comment.setUserId(userId);

        commentRepository.save(comment);

        Post post = postsRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setCommentCount(post.getCommentCount() + 1);

        kafkaTemplate.send(
                "post-commented-topic",
                postId,
                PostCommentedEvent.builder()
                        .postId(postId)
                        .commentedByUserId(userId)
                        .build()
        );
    }

    public List<com.rishbootdev.postsservice.dto.CommentDto> getComments(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .filter(c -> !c.isDeleted())
                .map(comment -> {
                    com.rishbootdev.postsservice.dto.CommentDto dto = modelMapper.map(comment, com.rishbootdev.postsservice.dto.CommentDto.class);
                    try {
                        com.rishbootdev.postsservice.dto.PersonDto person = profileClient.getProfileByUserId(comment.getUserId());
                        dto.setAuthor(new com.rishbootdev.postsservice.dto.AuthorDto(
                                person.getUserId(),
                                person.getFirstName() + " " + person.getLastName(),
                                person.getHeadline(),
                                person.getProfilePictureUrl()
                        ));
                    } catch (Exception e) {
                        dto.setAuthor(new com.rishbootdev.postsservice.dto.AuthorDto(comment.getUserId(), "Unknown User", "Member", ""));
                    }
                    return dto;
                })
                .toList();
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Long userId = UserContextHolder.getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("Unauthorized");
        }

        PostComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUserId().equals(userId)) {
            throw new RuntimeException("You can delete only your own comment");
        }

        comment.setDeleted(true);
        comment.setContent("[deleted]");

        Post post = postsRepository.findById(comment.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setCommentCount(
                Math.max(0, post.getCommentCount() - 1)
        );

        log.info("Comment {} deleted by user {}", commentId, userId);
    }
}

