package event;

import com.google.gson.Gson;
import infra.message.INotifier;
import infra.message.Notifier;
import model.MessageType;
import model.Order;

import java.util.logging.Logger;

public class EventPublisher {
    private static final Logger logger = Logger.getLogger(EventPublisher.class.getName());
    private final INotifier notifier;
    private final Gson gson;

    public EventPublisher() {
        this.notifier = new Notifier();
        this.gson = new Gson();
    }

    public void publish(String eventName, Order order) {
        logger.info("Evento publicado: " + eventName);
        logger.info("Detalhes do evento:");
        logger.info("- Pedido ID: " + order.getId());
        logger.info("- Usuário ID: " + order.getUser().getId());
        logger.info("- Total: R$" + order.getTotal());
        logger.info("- Status: " + order.getStatus());
        
        if (eventName.equals("OrderCreated")) {
            publishEmailEvent("ORDER_CONFIRMATION", order);
        } else if (eventName.equals("OrderStatusUpdated")) {
            publishEmailEvent("ORDER_STATUS_UPDATE", order);
        }
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
