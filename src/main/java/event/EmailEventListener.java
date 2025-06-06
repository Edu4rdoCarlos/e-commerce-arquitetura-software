package event;

import com.rabbitmq.client.*;
import model.MessageType;
import service.EmailService;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

public class EmailEventListener {
    private static final Logger logger = Logger.getLogger(EmailEventListener.class.getName());
    private final EmailService emailService;
    private final Gson gson;
    private Connection connection;
    private Channel channel;

    public EmailEventListener(EmailService emailService) {
        this.emailService = emailService;
        this.gson = new Gson();
    }

    public void start() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setUsername("guest");
            factory.setPassword("guest");

            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(MessageType.EMAIL.name(), false, false, false, null);
            logger.info("EmailEventListener iniciado. Aguardando mensagens...");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                logger.info("Mensagem recebida: " + message);
                
                try {
                    EmailEvent emailEvent = gson.fromJson(message, EmailEvent.class);
                    processEmailEvent(emailEvent);
                } catch (Exception e) {
                    logger.severe("Erro ao processar evento de email: " + e.getMessage());
                }
            };

            channel.basicConsume(MessageType.EMAIL.name(), true, deliverCallback, consumerTag -> {});
        } catch (IOException | TimeoutException e) {
            logger.severe("Erro ao iniciar EmailEventListener: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void processEmailEvent(EmailEvent event) {
        logger.info("Processando evento de email do tipo: " + event.getEventType());
        
        switch (event.getEventType()) {
            case "ORDER_CONFIRMATION":
                emailService.sendOrderConfirmationEmail(event.getOrder());
                break;
            case "ORDER_STATUS_UPDATE":
                emailService.sendOrderStatusUpdateEmail(event.getOrder());
                break;
            default:
                emailService.sendEmail(event.getTo(), event.getSubject(), event.getBody());
        }
    }

    public void stop() {
        try {
            if (channel != null && channel.isOpen()) {
                channel.close();
            }
            if (connection != null && connection.isOpen()) {
                connection.close();
            }
            logger.info("EmailEventListener parado com sucesso.");
        } catch (IOException | TimeoutException e) {
            logger.severe("Erro ao parar EmailEventListener: " + e.getMessage());
        }
    }
}
