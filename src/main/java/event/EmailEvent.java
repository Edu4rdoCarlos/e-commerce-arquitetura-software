package event;

import domain.model.Order;

public class EmailEvent {
    private String to;
    private String subject;
    private String body;
    private String eventType;
    private Order order;

    public EmailEvent(String to, String subject, String body, String eventType, Order order) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.eventType = eventType;
        this.order = order;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getEventType() {
        return eventType;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "EmailEvent{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", eventType='" + eventType + '\'' +
                ", total=" + order.getTotal() +
                '}';
    }
}
