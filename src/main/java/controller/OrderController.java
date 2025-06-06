package controller;

import model.Order;
import model.Product;
import model.User;
import service.OrderService;

import java.util.List;
import java.util.logging.Logger;

public class OrderController {
    private static final Logger logger = Logger.getLogger(OrderController.class.getName());

    private OrderService orderService = new OrderService();

    public void createOrder(User user, List<Product> products) {
        orderService.createOrder(user, products);
    }

    public void listOrders(User user) {
        List<Order> orders = orderService.getOrdersByUser(user);
        logger.info("Pedidos do usuário:");
        for (Order o : orders) {
            logger.info("- Pedido #" + o.getId() + ": Total R$" + o.getTotal() + " | Status: " + o.getStatus());
        }
    }

    public void viewOrderDetails(Long id) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            logger.info("Detalhes do Pedido #" + order.getId() + ":");
            logger.info("Total: R$" + order.getTotal());
            logger.info("Status: " + order.getStatus());
        } else {
            logger.warning("Pedido não encontrado.");
        }
    }
}
