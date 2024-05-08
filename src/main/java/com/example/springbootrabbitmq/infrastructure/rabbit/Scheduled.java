package com.example.springbootrabbitmq.infrastructure.rabbit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author duchieu212
 */
@Component
public class Scheduled {

    private RabbitProducer rabbitProducer;

    @Autowired
    public Scheduled(RabbitProducer rabbitProducer) {
        this.rabbitProducer = rabbitProducer;
    }

    static int number = 0;

    @org.springframework.scheduling.annotation.Scheduled(fixedRate = 3000)
    void send() {
        rabbitProducer.sendMessage("" + number++);
    }
}
