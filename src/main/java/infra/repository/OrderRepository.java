package infra.repository;

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
    public Long saveOrder(Long userId, double total, String status) throws SQLException {
        Connection conn = DatabaseConnection.getInstance();
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO orders (user_id, total, status) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        ps.setLong(1, userId);
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
        return orderId;
    }

    public List<Order> findByUser(User user) throws SQLException {
        List<Order> orders = new ArrayList<>();
        Connection conn = DatabaseConnection.getInstance();
        PreparedStatement ps = conn.prepareStatement(
                "SELECT id, total, status FROM orders WHERE user_id = ?");
        ps.setLong(1, user.getId());
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Order order = new Order(
                    rs.getLong("id"),
                    user,
                    rs.getDouble("total"),
                    rs.getString("status"));
            orders.add(order);
        }

        rs.close();
        ps.close();
        conn.close();
        return orders;
    }

    public Order findById(Long id) throws SQLException {
        Connection conn = DatabaseConnection.getInstance();
        PreparedStatement ps = conn.prepareStatement(
                "SELECT user_id, total, status FROM orders WHERE id = ?");
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Long userId = rs.getLong("user_id");
            double total = rs.getDouble("total");
            String status = rs.getString("status");
            User user = new User(userId, "Desconhecido", "email@placeholder.com", "", null);

            Order order = new Order(id, user, total, status);
            rs.close();
            ps.close();
            conn.close();
            return order;
        }

        rs.close();
        ps.close();
        conn.close();
        return null;
    }

    public boolean updateStatus(Long orderId, String newStatus) throws SQLException {
        Connection conn = DatabaseConnection.getInstance();
        PreparedStatement ps = conn.prepareStatement(
                "UPDATE orders SET status = ? WHERE id = ?");
        ps.setString(1, newStatus);
        ps.setLong(2, orderId);
        int rows = ps.executeUpdate();
        ps.close();
        conn.close();
        return rows > 0;
    }

}