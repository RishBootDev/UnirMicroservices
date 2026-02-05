package com.rishbootdev.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ConnectionService")
public interface ConnectionClient {

    @PostMapping("/connections/internal/sync")
    void syncPerson(@RequestParam("userId") Long userId, @RequestParam("name") String name);
}
