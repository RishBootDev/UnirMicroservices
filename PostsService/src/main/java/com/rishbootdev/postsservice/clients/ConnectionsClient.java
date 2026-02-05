package com.rishbootdev.postsservice.clients;


import com.rishbootdev.postsservice.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "CONNECTIONSERVICE", path = "/connections")
public interface ConnectionsClient {

    @GetMapping("/first-degree")
    List<PersonDto> getFirstConnections();

}
