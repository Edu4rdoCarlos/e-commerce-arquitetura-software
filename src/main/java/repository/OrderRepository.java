package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.model.Order;
import domain.model.User;
import infra.DatabaseConnection;

public class OrderRepository {

    public Long saveOrder(User user, double total, String status) {
        Long orderId = null;
        try {
            Connection conn = DatabaseConnection.getInstance();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO orders (user_id, total, status) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            ps.setLong(1, user.getId());
            ps.setDouble(2, total);
            ps.setString(3, status);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getLong(1);
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderId;
    }

    public List<Order> findOrdersByUser(User user) {
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
                    rs.getString("status"), null
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

    public Order findById(Long id) {
        Order order = null;
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

                order = new Order(id, user, total, status, total);
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }
}
