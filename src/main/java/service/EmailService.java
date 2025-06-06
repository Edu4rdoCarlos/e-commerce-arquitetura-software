package service;

import java.util.logging.Logger;

import com.google.gson.Gson;
import domain.model.MessageType;
import domain.model.Order;
import event.EmailEvent;
import infra.message.INotifier;
import infra.message.Notifier;

public class EmailService {
    private static final Logger logger = Logger.getLogger(EmailService.class.getName());
    private final INotifier notifier;
    private final Gson gson;

    public EmailService() {
        this.notifier = new Notifier();
        this.gson = new Gson();
    }

    public void publishEmailEvent(String eventType, Order order) {
        String to = order.getUser().getEmail();
        String subject = eventType.equals("ORDER_CONFIRMATION") ?
                "Confirmação do Pedido #" + order.getId() :
                "Atualização do Pedido #" + order.getId();

        EmailEvent emailEvent = new EmailEvent(to, subject, "", eventType, order);

        String jsonMessage = gson.toJson(emailEvent);
        notifier.sendMessage(jsonMessage, MessageType.EMAIL);

        logger.info("Evento de email publicado: " + eventType);
    }
}
