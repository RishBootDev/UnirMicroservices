package com.rishbootdev.profileservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "USERSERVICE", path = "/user")
public interface UserClient {


    @GetMapping("/getUserId")
    Long getUserIdFromEmail(String email);
}
