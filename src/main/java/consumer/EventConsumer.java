package consumer;

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import event.EmailEvent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EventConsumer extends DefaultConsumer {
    public EventConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, StandardCharsets.UTF_8);
        Gson gson = new Gson();
        EmailEvent emailEvent = gson.fromJson(message, EmailEvent.class);
        System.out.println("Message received: " + emailEvent);
    }
}
