package com.example.springbootrabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootRabbitMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRabbitMqApplication.class, args);
    }

}
