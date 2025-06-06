package service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import domain.model.Cart;
import domain.model.Order;
import domain.model.Product;
import domain.model.User;
import event.EventPublisher;
import filters.InventoryCheckFilter;
import filters.OrderPipeline;
import filters.ShippingValidationFilter;
import infra.repository.OrderRepository;

public class OrderService {
    private static final Logger logger = Logger.getLogger(OrderService.class.getName());
    private final EventPublisher eventPublisher = new EventPublisher();
    private final OrderPipeline pipeline;
    private final OrderRepository orderRepository = new OrderRepository();

    public OrderService() {
        pipeline = new OrderPipeline();
        pipeline.addFilter(new InventoryCheckFilter());
        pipeline.addFilter(new ShippingValidationFilter());
    }

    public void createOrder(Cart cart) {
        try {
            pipeline.execute(cart);
            String status = "PROCESSING";
            double total = cart.getProducts().stream().mapToDouble(Product::getPrice).sum();

            Long orderId = orderRepository.saveOrder(cart.getUser().getId(), total, status);

            Order order = new Order(orderId, cart.getUser(), total, status);
            logger.info("Pedido criado com sucesso! Total: R$ " + total);
            eventPublisher.publish("OrderCreated", order);
        } catch (SQLException e) {
            logger.severe("Erro ao criar pedido: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> getOrdersByUser(User user) {
        try {
            return orderRepository.findByUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Order getOrderById(Long id) {
        try {
            return orderRepository.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Order updateOrderStatus(Long orderId, String newStatus) {
        try {
            boolean updated = orderRepository.updateStatus(orderId, newStatus);
            if (updated) {
                Order updatedOrder = getOrderById(orderId);
                if (updatedOrder != null) {
                    logger.info("Status do pedido #" + orderId + " atualizado para: " + newStatus);
                    eventPublisher.publish("OrderStatusUpdated", updatedOrder);
                    return updatedOrder;
                }
            }
        } catch (SQLException e) {
            logger.severe("Erro ao atualizar status do pedido: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}