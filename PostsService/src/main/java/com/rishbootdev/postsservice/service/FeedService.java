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
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final ConnectionsClient connectionsClient;
    private final ProfileClient profileClient;
    private final PostsRepository postsRepository;
    private final ModelMapper modelMapper;

    public Page<PostDto> getFeed(int page, int size) {

        List<PersonDto> connections = connectionsClient.getFirstConnections();
        List<Long> userIds = new ArrayList<>();

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

        return postsPage.map(post -> {
            PostDto dto = modelMapper.map(post, PostDto.class);
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
