package service;

import com.google.gson.Gson;
import domain.model.MessageType;
import domain.model.Order;
import event.ChargeEvent;
import infra.message.INotifier;
import infra.message.Notifier;

import java.util.logging.Logger;

public class BillingService {
    private static final Logger logger = Logger.getLogger(BillingService.class.getName());
    private final INotifier notifier;
    private final Gson gson;

    public BillingService() {
        this.notifier = new Notifier();
        this.gson = new Gson();
    }

    public void publishChargeEvent(Order order) {
        String to = order.getUser().getEmail();
        String subject = "Cobrança do Pedido #" + order.getId();

        ChargeEvent emailEvent = new ChargeEvent(to, subject, "Nas próximas horas, uma cobrança vai ser feita no valor de: ", order.getTotal(), order);

        String jsonMessage = gson.toJson(emailEvent);
        notifier.sendMessage(jsonMessage, MessageType.CHARGE);

        logger.info("Evento de cobrança publicado.");
    }

    public void publishInvoiceEvent(Order order) {
        String to = order.getUser().getEmail();
        String subject = "Fatura do Pedido #" + order.getId();

        ChargeEvent emailEvent = new ChargeEvent(to, subject, "Seu pedido foi faturado! Agradecemos a sua compra.", order.getTotal(), order);

        String jsonMessage = gson.toJson(emailEvent);
        notifier.sendMessage(jsonMessage, MessageType.INVOICE);

        logger.info("Evento de fatura publicado.");
    }
}