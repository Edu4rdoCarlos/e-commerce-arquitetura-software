package event;

import java.util.logging.Logger;

import domain.model.Order;

public class EventPublisher {
    private static final Logger logger = Logger.getLogger(EventPublisher.class.getName());

    public void publish(String eventName, Order order) {
        logger.info("Evento publicado: " + eventName);
        logger.info("Detalhes do evento:");
        logger.info("- Pedido ID: " + order.getId());
        logger.info("- Usuário ID: " + order.getUser().getId());
        logger.info("- Total: R$" + order.getTotal());
        logger.info("- Status: " + order.getStatus());
    }
}

