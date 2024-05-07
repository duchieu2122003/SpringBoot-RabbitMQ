package com.example.springbootrabbitmq.controller;

import com.example.springbootrabbitmq.infrastructure.logger.LoggerObject;
import com.example.springbootrabbitmq.infrastructure.rabbit.RabbitProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duchieu212
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class MessageController {

    private final RabbitProducer rabbitProducer;

    private LoggerObject loggerObject;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @GetMapping("/publish")
    public ResponseEntity<String> send() {
//        LoggerObject loggerObject = new LoggerObject();
//        loggerObject.setContent(message);
        String message = "aaaaaaaa";
        rabbitProducer.sendMessage(message);
        return ResponseEntity.ok("Producer send message to queue: " + queueName + " - host: " + host);
    }
}
