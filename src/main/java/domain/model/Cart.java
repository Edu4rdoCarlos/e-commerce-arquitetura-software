package domain.model;

import java.util.List;

public class Cart {
    private Long id;
    private User user;
    private List<Product> products;

    private double shipping;

    public Cart(Long id, User user, List<Product> products) {
        this.id = id;
        this.user = user;
        this.products = products;
        this.shipping = 30.0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    public double getShipping() { return shipping; }
    public void setShipping(double shipping) { this.shipping = shipping; }
}
