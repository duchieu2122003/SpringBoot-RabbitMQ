# Đăng ký tài khoản Rabbit CloudAMQP
   Vào link https://www.cloudamqp.com/
   
# Cấu hình RabbitMQ: Offline hoặc Online
  1. Cấu hình rabbit offline
  #spring.rabbitmq.host=localhost
  #spring.rabbitmq.port=5672
  #spring.rabbitmq.username=guest
  #spring.rabbitmq.password=guest

  2.Cấu hình rabbit cloud
  spring.rabbitmq.host=fly.rmq.cloudamqp.com
  spring.rabbitmq.port=5672
  spring.rabbitmq.username=qaqvjoai
  spring.rabbitmq.password=GRvZLDaupWcZY7YRVX0VXM9BoX3NT6C2
  spring.rabbitmq.virtual-host=qaqvjoai
  
# Đầu tiên cần add dependence để làm việc với RabbitMQ
     <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
# Thêm dependence Gson để chuyển đổi object send qua Json
   <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.8.9</version>
        </dependency>
# Cấu hình Queue trong application
    rabbit.queue.name=demo-rabbit-queue
    rabbit.topic.exchange=demo-rabbit-exchange-log
    rabbit.routing.key=demo-rabbit-key
    path.file.csv= log-project/
    
# Cấu hình RabbitConfig
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

# Cấu hình Producer để truyền dữ liệu

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


#. Cấu hình Consumer để nhận và xử lý dữ liệu





