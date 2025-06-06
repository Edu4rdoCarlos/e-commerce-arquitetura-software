package infra.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.model.Order;
import domain.model.Product;
import domain.model.User;
import infra.DatabaseConnection;

public class AdminRepository {

    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();

        Connection conn = DatabaseConnection.getInstance();
        PreparedStatement ps = conn.prepareStatement(
                "SELECT o.id, o.total, o.status, u.id as user_id, u.name, u.email " +
                        "FROM orders o JOIN users u ON o.user_id = u.id"
        );
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            User user = new User(
                    rs.getLong("user_id"),
                    rs.getString("name"),
                    rs.getString("email")
            );


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

        return orders;
    }

    public boolean updateOrderStatus(Long orderId, String newStatus) throws SQLException {
        Connection conn = DatabaseConnection.getInstance();
        PreparedStatement ps = conn.prepareStatement(
                "UPDATE orders SET status = ? WHERE id = ?"
        );
        ps.setString(1, newStatus);
        ps.setLong(2, orderId);
        int rows = ps.executeUpdate();

        ps.close();
        conn.close();
        return rows > 0;
    }

    public void createProduct(Product product) throws SQLException {
        Connection conn = DatabaseConnection.getInstance();
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO products (name, price, stock) VALUES (?, ?, ?)"
        );
        ps.setString(1, product.getName());
        ps.setDouble(2, product.getPrice());
        ps.setInt(3, product.getStock());
        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public void updateProduct(Product product) throws SQLException {
        Connection conn = DatabaseConnection.getInstance();
        PreparedStatement ps = conn.prepareStatement(
                "UPDATE products SET name = ?, price = ?, stock = ? WHERE id = ?"
        );
        ps.setString(1, product.getName());
        ps.setDouble(2, product.getPrice());
        ps.setInt(3, product.getStock());
        ps.setLong(4, product.getId());
        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public void deleteProduct(Long id) throws SQLException {
        Connection conn = DatabaseConnection.getInstance();
        PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM products WHERE id = ?"
        );
        ps.setLong(1, id);
        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public void createUser(User user) throws SQLException {
        Connection conn = DatabaseConnection.getInstance();
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users (name, email) VALUES (?, ?)"
        );
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public void deleteUser(Long id) throws SQLException {
        Connection conn = DatabaseConnection.getInstance();
        PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM users WHERE id = ?"
        );
        ps.setLong(1, id);
        ps.executeUpdate();

        ps.close();
        conn.close();
    }
}
