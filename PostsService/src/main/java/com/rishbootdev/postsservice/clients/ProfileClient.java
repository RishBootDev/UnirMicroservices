package com.rishbootdev.postsservice.clients;

import com.rishbootdev.postsservice.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PROFILESERVICE", path = "/profile")
public interface ProfileClient {

    @GetMapping("/{userId}")
    PersonDto getProfileByUserId(@PathVariable("userId") Long userId);
}
