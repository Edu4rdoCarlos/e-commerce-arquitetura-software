package model;

public class Order {
    private Long id;
    private User user;
    private Double total;
    private String status;

    public Order(Long id, User user, Double total, String status) {
        this.id = id;
        this.user = user;
        this.total = total;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
