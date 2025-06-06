package controller;

import java.util.List;
import java.util.logging.Logger;

import domain.model.Cart;
import domain.model.Order;
import domain.model.User;
import service.OrderService;

public class OrderController {
    private static final Logger logger = Logger.getLogger(OrderController.class.getName());

    private OrderService orderService = new OrderService();

    public void createOrder(Cart cart) {
        orderService.createOrder(cart);
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
