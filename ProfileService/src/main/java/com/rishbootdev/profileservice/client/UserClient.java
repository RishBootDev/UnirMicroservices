package com.rishbootdev.profileservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "UserService",
        path = "/user"
)
public interface UserClient {

    @GetMapping("/getUserId/{email}")
    Long getUserIdFromEmail(@PathVariable("email") String email);
}
