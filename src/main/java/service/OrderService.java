package service;

import model.Order;
import model.Product;
import model.User;
import infra.DatabaseConnection;
import event.EventPublisher;
import filter.InventoryCheckFilter;
import filter.ShippingCalculationFilter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class OrderService {
    private static final Logger logger = Logger.getLogger(OrderService.class.getName());

    private EventPublisher eventPublisher = new EventPublisher();
    private InventoryCheckFilter inventoryFilter = new InventoryCheckFilter();
    private ShippingCalculationFilter shippingFilter = new ShippingCalculationFilter();

    public void createOrder(User user, List<Product> products) {
        try {
            Connection conn = DatabaseConnection.getInstance();

            if (!inventoryFilter.apply(products)) {
                logger.warning("Erro: produto sem estoque.");
                return;
            }

            double shippingCost = shippingFilter.apply(products);
            double total = products.stream().mapToDouble(Product::getPrice).sum() + shippingCost;
            String status = "PROCESSING";

            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO orders (user_id, total, status) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            ps.setLong(1, user.getId());
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

            Order order = new Order(orderId, user, total, status);
            logger.info("Pedido criado com sucesso! Total: R$ " + total);
            eventPublisher.publish("OrderCreated", order);

        } catch (SQLException e) {
            e.printStackTrace();
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
}
