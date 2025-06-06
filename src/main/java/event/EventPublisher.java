package event;

import java.util.logging.Logger;

import com.google.gson.Gson;

import domain.model.MessageType;
import domain.model.Order;
import infra.message.INotifier;
import infra.message.Notifier;
import service.BillingService;
import service.EmailService;

public class EventPublisher {
    private static final Logger logger = Logger.getLogger(EventPublisher.class.getName());
    private final INotifier notifier;
    private final Gson gson;
    private final EmailService emailService;
    private final BillingService billingService;
    public EventPublisher() {
        this.notifier = new Notifier();
        this.gson = new Gson();
        this.emailService = new EmailService();
        this.billingService = new BillingService();
    }

    public void publish(String eventName, Order order) {
        logger.info("- Pedido ID: " + order.getId());
        logger.info("- Usuário ID: " + order.getUser().getId());
        logger.info("- Total: R$" + order.getTotal());
        logger.info("- Status: " + order.getStatus());
        
        if (eventName.equals("OrderCreated")) {
            emailService.publishEmailEvent("ORDER_CONFIRMATION", order);
        } else if (eventName.equals("OrderStatusUpdated")) {
            emailService.publishEmailEvent("ORDER_STATUS_UPDATE", order);
        }
        billingService.publishChargeEvent(order);
        billingService.publishInvoiceEvent(order);
    }
}
