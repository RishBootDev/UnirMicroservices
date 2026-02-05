package com.rishbootdev.connectionservice.controller;

import com.rishbootdev.connectionservice.entity.Person;
import com.rishbootdev.connectionservice.service.ConnectionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/connections")
@RequiredArgsConstructor
public class ConnectionsController {

    private final ConnectionsService connectionsService;

    @GetMapping("/first-degree")
    public ResponseEntity<List<Person>> getFirstConnections() {
        return ResponseEntity.ok(connectionsService.getFirstDegreeConnections());
    }

    @GetMapping("/requests")
    public ResponseEntity<List<Person>> getIncomingConnectionRequests() {
        return ResponseEntity.ok(connectionsService.getIncomingConnectionRequests());
    }

    @PostMapping("/request/{userId}")
    public ResponseEntity<Boolean> sendConnectionRequest(@PathVariable Long userId) {
        return ResponseEntity.ok(connectionsService.sendConnectionRequest(userId));
    }

    @PostMapping("/accept/{userId}")
    public ResponseEntity<Boolean> acceptConnectionRequest(@PathVariable Long userId) {
        return ResponseEntity.ok(connectionsService.acceptConnectionRequest(userId));
    }

    @PostMapping("/reject/{userId}")
    public ResponseEntity<Boolean> rejectConnectionRequest(@PathVariable Long userId) {
        return ResponseEntity.ok(connectionsService.rejectConnectionRequest(userId));
    }

    @PostMapping("/internal/sync")
    public ResponseEntity<Void> syncPerson(@RequestParam Long userId, @RequestParam String name) {
        connectionsService.syncPerson(userId, name);
        return ResponseEntity.ok().build();
    }
}