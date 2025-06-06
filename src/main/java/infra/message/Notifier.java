package infra.message;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import domain.model.MessageType;

public class Notifier implements INotifier {
    @Override
    public void sendMessage(String message, MessageType messageType) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            System.out.println("Sending message...");

            channel.queueDeclare(messageType.name(), true, false, false, null);
            channel.basicPublish("", messageType.name(), null, message.getBytes(StandardCharsets.UTF_8));

            channel.close();
            connection.close();
        } catch (IOException | TimeoutException io) {
            throw new RuntimeException(io);
        }
    }
}
