package consumer;

import model.MessageType;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class EmailConsumer {
    private final String queueName = MessageType.EMAIL.name();

    private final Channel channel;
    private final Consumer consumer;

    public EmailConsumer() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("localhost");

        try {
            Connection connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(queueName, true, false, false, null);

            consumer = new EventConsumer(channel);
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void consume() {
        try {
            channel.basicConsume(queueName, true, consumer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
