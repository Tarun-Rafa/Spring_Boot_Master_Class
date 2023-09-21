# Spring_Boot_Master_Class
Spring Boot Master Class

Creating a source, processor, and sink using Spring Boot and Spring Cloud Stream is straightforward. Spring Cloud Stream abstracts the binder layer (i.e., messaging middleware) allowing developers to focus on writing the business logic.

For this example, we'll use RabbitMQ as the messaging middleware, but Spring Cloud Stream also supports others like Kafka.

Let's dive in.

### 1. **Setting up RabbitMQ**:

If you don't have RabbitMQ running, you can start it with Docker:

```bash
docker run -d --name rabbitmq -p 15672:15672 -p 5672:5672 rabbitmq:management
```

### 2. **Creating a Source (Producer)**:

This source will produce a message every second.

1. Create a new Spring Boot project via Spring Initializr, adding the "Cloud Stream" and "RabbitMQ" dependencies.
2. Define a source:

```java
@EnableBinding(Source.class)
public class TimeSource {

    @Autowired
    private Source source;

    @Scheduled(fixedRate = 1000)
    public void publishTime() {
        source.output().send(MessageBuilder.withPayload(new Date().toString()).build());
    }
}
```

3. Configure application.properties:

```properties
spring.cloud.stream.bindings.output.destination=myTopic
```

### 3. **Creating a Processor**:

This processor will convert the given date string to uppercase.

1. Create a new Spring Boot project with the same dependencies as above.
2. Define a processor:

```java
@EnableBinding(Processor.class)
public class UppercaseProcessor {

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public String process(String payload) {
        return payload.toUpperCase();
    }
}
```

3. Configure application.properties:

```properties
spring.cloud.stream.bindings.input.destination=myTopic
spring.cloud.stream.bindings.output.destination=processedTopic
```

### 4. **Creating a Sink (Consumer)**:

This sink will simply log the received message.

1. Create another Spring Boot project with the same dependencies.
2. Define a sink:

```java
@EnableBinding(Sink.class)
public class LoggerSink {

    @StreamListener(Sink.INPUT)
    public void log(String payload) {
        System.out.println("Received: " + payload);
    }
}
```

3. Configure application.properties:

```properties
spring.cloud.stream.bindings.input.destination=processedTopic
```

### 5. **Run the Applications**:

1. Start RabbitMQ (if it's not already running).
2. Start the source, processor, and sink applications.
3. You should see the sink application logging the date messages in uppercase every second.

### Conclusion:

With the combination of Spring Boot and Spring Cloud Stream, we've quickly created a source, processor, and sink without dealing with the specifics of the messaging system (RabbitMQ in this case). This abstraction helps in quickly building, scaling, and modifying data-driven applications.

Always ensure you add the necessary error handling, logging, and other crucial functionalities when building production-grade applications.
