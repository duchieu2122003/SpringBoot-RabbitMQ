package com.example.springbootrabbitmq.infrastructure.rabbit;

import com.example.springbootrabbitmq.infrastructure.logger.LoggerObject;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

/**
 * @author duchieu212
 */
@Service
@Slf4j

public class RabbitProducerService {

    @Value("${rabbitmq.topic.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Value("${path.file.csv}")
    private String pathFolder;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        try{
            log.info("MESSAGE STRING send =>>>>>>>>> " + message);
            rabbitTemplate.convertAndSend(message);
        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendLogMessage(LoggerObject loggerObject) {
        log.info("MESSAGE OBJECT =>>>>>>>>>>>>>>>>");
        loggerObject.setPathFile(pathFolder + loggerObject.getPathFile());
        Gson gson = new Gson();
        String message = gson.toJson(loggerObject);
        rabbitTemplate.convertAndSend(exchange, routingKey, message, messagePostProcessor -> {
            messagePostProcessor.getMessageProperties();
            return messagePostProcessor;
        });
    }

}
