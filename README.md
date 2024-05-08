# 1. Đăng ký tài khoản Rabbit CloudAMQP
   Vào link https://www.cloudamqp.com/
   
# 2. Cấu hình RabbitMQ: Offline hoặc Online
  - Máy ảo
   Build Docker:
      docker pull rabbitmq
      docker run -it --rm --name rabbitmqnametest -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management
      Tài khoản: guest
      Mật khẩu: guest
      Host: http://localhost:15672/#/
  - Cấu hình rabbit offline
  #spring.rabbitmq.host=localhost
  #spring.rabbitmq.port=5672
  #spring.rabbitmq.username=guest
  #spring.rabbitmq.password=guest

  - Cấu hình rabbit cloud
  spring.rabbitmq.host=fly.rmq.cloudamqp.com
  spring.rabbitmq.port=5672
  spring.rabbitmq.username=qaqvjoai
  spring.rabbitmq.password=GRvZLDaupWcZY7YRVX0VXM9BoX3NT6C2
  spring.rabbitmq.virtual-host=qaqvjoai
  
# 3. Đầu tiên cần add dependence để làm việc với RabbitMQ
     <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
# 4. Thêm dependence Gson để chuyển đổi object send qua Json
   <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.8.9</version>
        </dependency>
# 5. Cấu hình Queue trong application
    rabbit.queue.name=demo-rabbit-queue
    rabbit.topic.exchange=demo-rabbit-exchange-log
    rabbit.routing.key=demo-rabbit-key
    path.file.csv= log-project/
    
# 6. Cấu hình RabbitConfig
@Configuration
public class RabbitConfig {

    @Value("${rabbit.queue.name}")
    private String queueName;

    @Value("${rabbit.topic.exchange}")
    private String topicExchange;

    @Value("${rabbit.routing.key}")
    private String routingKey;

    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(topicExchange);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }
}

# 7. Cấu hình Producer để truyền dữ liệu

@Service
@Slf4j
@EnableAutoConfiguration
public class RabbitProducerService {

    @Value("${rabbit.queue.name}")
    private String exchange;

    @Value("${rabbit.routing.key}")
    private String routingKey;

    @Value("${path.file.csv}")
    private String pathFolder;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        log.info("MESSAGE STRING send =>>>>>>>>> " + message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
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


# 8. Cấu hình Consumer để nhận và xử lý dữ liệu

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
