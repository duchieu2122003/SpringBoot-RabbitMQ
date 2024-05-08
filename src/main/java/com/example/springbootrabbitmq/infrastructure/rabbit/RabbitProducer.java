package com.example.springbootrabbitmq.infrastructure.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;


/**
 * @author duchieu212
 */
@Service
@EnableAutoConfiguration
public class RabbitProducer {

    @Value("${rabbitmq.topic.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;

    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        System.err.println(String.format("SENDING ==>> ", message));
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

//    public void sendLogMessage(LoggerObject loggerObject) {
//        loggerObject.setPathFile(pathFolder + loggerObject.getPathFile());
//        Gson gson = new Gson();
//        String message = gson.toJson(loggerObject);
//        rabbitTemplate.convertAndSend(exchange, routingKey, message, messagePostProcessor -> {
//            messagePostProcessor.getMessageProperties();
//            return messagePostProcessor;
//        });
//    }

}
