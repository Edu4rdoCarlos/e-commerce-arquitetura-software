package event;

import domain.model.Order;

public class ChargeEvent {
    private String to;
    private String subject;
    private String body;
    private Double value;
    private Order order;

    public ChargeEvent(String to, String subject, String body, Double value, Order order) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.value = value;
        this.order = order;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
