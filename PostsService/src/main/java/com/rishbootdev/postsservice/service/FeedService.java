package com.rishbootdev.postsservice.service;


import com.rishbootdev.postsservice.clients.ConnectionsClient;
import com.rishbootdev.postsservice.dto.PersonDto;
import com.rishbootdev.postsservice.dto.PostDto;
import com.rishbootdev.postsservice.entity.Post;
import com.rishbootdev.postsservice.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final ConnectionsClient connectionsClient;
    private final PostsRepository postsRepository;
    private final ModelMapper modelMapper;

    public Page<PostDto> getFeed(int page, int size) {

        List<PersonDto> connections = connectionsClient.getFirstConnections();

        if (connections.isEmpty()) {
            return Page.empty();
        }

        List<Long> userIds = connections.stream()
                .map(PersonDto::getUserId)
                .toList();

        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postsPage =
                postsRepository.findByUserIdInOrderByCreatedAtDesc(
                        userIds,
                        pageable
                );

        return postsPage.map(post ->
                modelMapper.map(post, PostDto.class)
        );
    }
}

