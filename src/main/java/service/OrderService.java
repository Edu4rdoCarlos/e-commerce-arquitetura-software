package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import domain.model.Cart;
import domain.model.Order;
import domain.model.Product;
import domain.model.User;
import event.EventPublisher;
import infra.DatabaseConnection;
import pipeline.InventoryCheckFilter;
import pipeline.OrderPipeline;
import pipeline.ShippingValidationFilter;

public class OrderService {
    private static final Logger logger = Logger.getLogger(OrderService.class.getName());

    private EventPublisher eventPublisher = new EventPublisher();
    private Connection conn;
    private OrderPipeline pipeline;
    public OrderService() {
        try {
            conn = DatabaseConnection.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        pipeline = new OrderPipeline();
        pipeline.addFilter(new InventoryCheckFilter());
        pipeline.addFilter(new ShippingValidationFilter());
    }

    public void createOrder(Cart cart) {
        try {
            pipeline.execute(cart);

            String status = "PROCESSING";

            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO orders (user_id, total, status) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            double total = cart.getProducts().stream().mapToDouble(Product::getPrice).sum();

            ps.setLong(1, cart.getUser().getId());
            ps.setDouble(2, total);
            ps.setString(3, status);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            Long orderId = null;
            if (rs.next()) {
                orderId = rs.getLong(1);
            }

            rs.close();
            ps.close();
            conn.close();

            Order order = new Order(orderId, cart.getUser(), total, status);
            logger.info("Pedido criado com sucesso! Total: R$ " + total);
            eventPublisher.publish("OrderCreated", order);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> getOrdersByUser(User user) {
        List<Order> orders = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getInstance();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT id, total, status FROM orders WHERE user_id = ?"
            );
            ps.setLong(1, user.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order(
                    rs.getLong("id"),
                    user,
                    rs.getDouble("total"),
                    rs.getString("status")
                );
                orders.add(order);
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public Order getOrderById(Long id) {
        try {
            Connection conn = DatabaseConnection.getInstance();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT user_id, total, status FROM orders WHERE id = ?"
            );
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Long userId = rs.getLong("user_id");
                double total = rs.getDouble("total");
                String status = rs.getString("status");
                User user = new User(userId, "Desconhecido", "email@placeholder.com");

                Order order = new Order(id, user, total, status);
                rs.close();
                ps.close();
                conn.close();
                return order;
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Order updateOrderStatus(Long orderId, String newStatus) {
        try {
            Connection conn = DatabaseConnection.getInstance();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE orders SET status = ? WHERE id = ?"
            );
            ps.setString(1, newStatus);
            ps.setLong(2, orderId);
            int rowsAffected = ps.executeUpdate();
            ps.close();
            
            if (rowsAffected > 0) {
                Order updatedOrder = getOrderById(orderId);
                
                if (updatedOrder != null) {
                    logger.info("Status do pedido #" + orderId + " atualizado para: " + newStatus);
                    eventPublisher.publish("OrderStatusUpdated", updatedOrder);
                    return updatedOrder;
                }
            }
            
            conn.close();
        } catch (SQLException e) {
            logger.severe("Erro ao atualizar status do pedido: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
}