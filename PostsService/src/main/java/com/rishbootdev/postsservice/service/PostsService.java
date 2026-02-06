package com.rishbootdev.postsservice.service;

import com.rishbootdev.postsservice.auth.UserContextHolder;
import com.rishbootdev.postsservice.clients.ProfileClient;
import com.rishbootdev.postsservice.dto.AuthorDto;
import com.rishbootdev.postsservice.dto.PersonDto;
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
    private final ProfileClient profileClient;
    private final KafkaTemplate<Long, PostCreatedEvent> kafkaTemplate;
    private final KafkaTemplate<Long, PostDeletedEvent> postDeletedKafka;

    @Transactional
    public PostDto createPost(PostDto dto) {
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

        PostDto responseDto = modelMapper.map(saved, PostDto.class);
        enrichWithAuthor(responseDto, userId);
        return responseDto;
    }

    public PostDto getPost(Long id) {
        Post post = postsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        PostDto dto = modelMapper.map(post, PostDto.class);
        enrichWithAuthor(dto, post.getUserId());
        return dto;
    }

    public org.springframework.data.domain.Page<PostDto> getUserPosts(Long userId, org.springframework.data.domain.Pageable pageable) {
        org.springframework.data.domain.Page<Post> postsPage = postsRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        return postsPage.map(post -> {
            PostDto dto = modelMapper.map(post, PostDto.class);
            enrichWithAuthor(dto, post.getUserId());
            return dto;
        });
    }

    private void enrichWithAuthor(PostDto dto, Long userId) {
        try {
            PersonDto person = profileClient.getProfileByUserId(userId);
            dto.setAuthor(new AuthorDto(
                    person.getUserId(),
                    person.getFirstName() + " " + person.getLastName(),
                    person.getHeadline(),
                    person.getProfilePictureUrl()
            ));
        } catch (Exception e) {
            dto.setAuthor(new AuthorDto(userId, "Unknown User", "Member", ""));
        }
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
