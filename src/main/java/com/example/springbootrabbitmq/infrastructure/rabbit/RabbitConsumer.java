package com.example.springbootrabbitmq.infrastructure.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author duchieu212
 */
@Service
public class RabbitConsumer {

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String message) {
        try {
            System.err.println("Connect and received : " + message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
