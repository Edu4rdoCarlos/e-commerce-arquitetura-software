package model;

public class Payment {
    private Long id;
    private Order order;
    private String method;
    private String status;

    public Payment(Long id, Order order, String method, String status) {
        this.id = id;
        this.order = order;
        this.method = method;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
