package service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import domain.model.Order;
import domain.model.Product;
import domain.model.User;
import infra.repository.AdminRepository;

public class AdminService {

    private final AdminRepository repository = new AdminRepository();

    public List<Order> getAllOrders() {
        try {
            return repository.getAllOrders();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean updateOrderStatus(Long orderId, String newStatus) {
        try {
            return repository.updateOrderStatus(orderId, newStatus);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createProduct(Product product) {
        try {
            repository.createProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) {
        try {
            repository.updateProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(Long id) {
        try {
            repository.deleteProduct(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUser(User user) {
        try {
            repository.createUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(Long id) {
        try {
            repository.deleteUser(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
