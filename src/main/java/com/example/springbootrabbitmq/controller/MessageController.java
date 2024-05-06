package com.example.springbootrabbitmq.controller;

import com.example.springbootrabbitmq.infrastructure.rabbit.RabbitProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duchieu212
 */
@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {

    private final RabbitProducerService rabbitProducerService;

    @GetMapping("/publish")
    public ResponseEntity<String> send(@RequestParam("message") String message) {
        rabbitProducerService.sendMessage(message);
        return ResponseEntity.ok("SENT OKKK");
    }
}
