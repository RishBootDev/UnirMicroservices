package com.rishbootdev.profileservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;


@Component
@FeignClient(name = "USERSERVICE", path = "/user")
public interface UserClient {


    @GetMapping("/getUserId")
    Long getUserIdFromEmail(String email);
}
