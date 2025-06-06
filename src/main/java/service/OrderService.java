package service;

import java.util.List;
import java.util.logging.Logger;

import domain.model.Order;
import domain.model.Product;
import domain.model.User;
import event.EventPublisher;
import filter.InventoryCheckFilter;
import filter.ShippingCalculationFilter;
import repository.OrderRepository;

public class OrderService {
    private static final Logger logger = Logger.getLogger(OrderService.class.getName());

    private EventPublisher eventPublisher = new EventPublisher();
    private InventoryCheckFilter inventoryFilter = new InventoryCheckFilter();
    private ShippingCalculationFilter shippingFilter = new ShippingCalculationFilter();
    private OrderRepository orderRepository = new OrderRepository();

    public void createOrder(User user, List<Product> products) {
        if (!inventoryFilter.apply(products)) {
            logger.warning("Erro: produto sem estoque.");
            return;
        }

        double shippingCost = shippingFilter.apply(products);
        double total = products.stream().mapToDouble(Product::getPrice).sum() + shippingCost;
        String status = "PROCESSING";

        Long orderId = orderRepository.saveOrder(user, total, status);

        if (orderId != null) {
            Order order = new Order(orderId, user, total, status, total);
            logger.info("Pedido criado com sucesso! Total: R$ " + total);
            eventPublisher.publish("OrderCreated", order);
        } else {
            logger.warning("Erro ao salvar o pedido.");
        }
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findOrdersByUser(user);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
