package event;

public class InvoiceEvent {
    private String to;
    private String subject;
    private String body;
    private Double value;

    public InvoiceEvent(String to, String subject, String body, Double value) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.value = value;
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
}
