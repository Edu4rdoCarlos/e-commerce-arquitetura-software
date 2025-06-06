package pipeline;

import model.Order;
import model.Product;
import java.util.List;

public class OrderContext {
    public Order order;
    public double total;
    public List<Product> products;

    public OrderContext(Order order, double total, List<Product> products) {
        this.order = order;
        this.total = total;
        this.products = products;
    }
}
