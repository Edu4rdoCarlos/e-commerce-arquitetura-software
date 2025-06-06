package controller;

import java.util.List;
import java.util.logging.Logger;

import domain.model.Order;
import domain.model.Product;
import domain.model.User;
import service.AdminService;

public class AdminController {
    private static final Logger logger = Logger.getLogger(AdminController.class.getName());
    private AdminService adminService = new AdminService();

    public void listAllOrders() {
        List<Order> orders = adminService.getAllOrders();
        logger.info("Todos os pedidos realizados:");
        for (Order o : orders) {
            logger.info("Pedido " + o.getId() + " - R$" + o.getTotal() + " (" + o.getStatus() + ") - " + o.getUser().getName());
        }
    }

    public void updateOrderStatus(Long orderId, String newStatus) {
        boolean updated = adminService.updateOrderStatus(orderId, newStatus);
        if (updated) {
            logger.info("Status do pedido #" + orderId + " atualizado para '" + newStatus + "'.");
        } else {
            logger.warning("Não foi possível atualizar o status do pedido #" + orderId + ".");
        }
    }

    public void createProduct(Product product) {
        adminService.createProduct(product);
        logger.info("Produto '" + product.getName() + "' criado com sucesso.");
    }

    public void updateProduct(Product product) {
        adminService.updateProduct(product);
        logger.info("Produto #" + product.getId() + " atualizado com sucesso.");
    }

    public void deleteProduct(Long id) {
        adminService.deleteProduct(id);
        logger.info("Produto #" + id + " excluído com sucesso.");
    }

    public void createUser(User user) {
        adminService.createUser(user);
        logger.info("Usuário '" + user.getName() + "' criado com sucesso.");
    }

    public void deleteUser(Long id) {
        adminService.deleteUser(id);
        logger.info("Usuário #" + id + " excluído com sucesso.");
    }
}
