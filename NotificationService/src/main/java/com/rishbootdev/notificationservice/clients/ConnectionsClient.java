package com.rishbootdev.notificationservice.clients;


import com.rishbootdev.notificationservice.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "CONNECTIONSERVICE", path = "/connections")
public interface ConnectionsClient {

    @GetMapping("/first-degree")
    List<PersonDto> getFirstConnections(@RequestHeader("X-User-Id") Long userId);

}

