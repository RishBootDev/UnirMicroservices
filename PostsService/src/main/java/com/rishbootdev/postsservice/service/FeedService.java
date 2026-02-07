package com.rishbootdev.postsservice.service;

import com.rishbootdev.postsservice.clients.ConnectionsClient;
import com.rishbootdev.postsservice.clients.ProfileClient;
import com.rishbootdev.postsservice.dto.AuthorDto;
import com.rishbootdev.postsservice.dto.PersonDto;
import com.rishbootdev.postsservice.dto.PostDto;
import com.rishbootdev.postsservice.entity.Post;
import com.rishbootdev.postsservice.repository.PostsRepository;
import com.rishbootdev.postsservice.auth.UserContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final ConnectionsClient connectionsClient;
    private final ProfileClient profileClient;
    private final PostsRepository postsRepository;
    private final ModelMapper modelMapper;
    private final com.rishbootdev.postsservice.repository.PostLikeRepository postLikeRepository;

    public Page<PostDto> getFeed(int page, int size) {

        List<PersonDto> connections = connectionsClient.getFirstConnections();
        List<Long> userIds = new ArrayList<>();

        log.info("the connections are : {}",connections);

        if (!connections.isEmpty()) {
            userIds.addAll(connections.stream()
                    .map(PersonDto::getUserId)
                    .toList());
        }

        Long currentUserId = UserContextHolder.getCurrentUserId();
        if (currentUserId != null) {
            userIds.add(currentUserId);
        }

        if (userIds.isEmpty()) {
            return Page.empty();
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postsPage =
                postsRepository.findByUserIdInOrderByCreatedAtDesc(
                        userIds,
                        pageable
                );

        List<Long> likedPostIds = (currentUserId != null)
                ? postLikeRepository.findByUserIdAndPostIdIn(currentUserId, postsPage.getContent().stream().map(Post::getId).toList())
                .stream().map(like -> like.getPostId()).toList()
                : List.of();

        return postsPage.map(post -> {
            PostDto dto = modelMapper.map(post, PostDto.class);
            dto.setLikedByCurrentUser(likedPostIds.contains(post.getId()));
            try {
                PersonDto person = profileClient.getProfileByUserId(post.getUserId());
                dto.setAuthor(new AuthorDto(
                        person.getUserId(),
                        person.getFirstName() + " " + person.getLastName(),
                        person.getHeadline(),
                        person.getProfilePictureUrl()
                ));
            } catch (Exception e) {
                dto.setAuthor(new AuthorDto(post.getUserId(), "Unknown User", "Member", ""));
            }
            return dto;
        });
    }
}
