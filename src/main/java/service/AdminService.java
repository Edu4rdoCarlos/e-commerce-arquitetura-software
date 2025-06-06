package service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import domain.model.Order;
import domain.model.Product;
import domain.model.User;
import repository.AdminRepository;

public class AdminService {
    private AdminRepository adminRepository = new AdminRepository();

    public List<Order> getAllOrders() {
        try {
            return adminRepository.findAllOrders();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean updateOrderStatus(Long orderId, String newStatus) {
        try {
            return adminRepository.updateOrderStatus(orderId, newStatus);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createProduct(Product product) {
        try {
            adminRepository.saveProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) {
        try {
            adminRepository.updateProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(Long id) {
        try {
            adminRepository.deleteProduct(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUser(User user) {
        try {
            adminRepository.saveUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(Long id) {
        try {
            adminRepository.deleteUser(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}