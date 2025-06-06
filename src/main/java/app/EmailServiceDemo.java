package app;

import model.Order;
import model.User;
import service.OrderService;

import java.util.logging.Logger;

public class EmailServiceDemo {
    private static final Logger logger = Logger.getLogger(EmailServiceDemo.class.getName());

    public static void main(String[] args) {
        logger.info("Iniciando demonstração do Email Service...");
        
        OrderService orderService = new OrderService();
        
        User user = new User(1L, "João Silva", "joao.silva@example.com");
        
        Order order = orderService.getOrderById(1L);
        if (order == null) {
            logger.info("Nenhum pedido encontrado com ID 1. Considere criar um pedido primeiro.");
            logger.info("Esta demonstração requer um pedido existente no banco de dados.");
            return;
        }
        
        logger.info("Atualizando status do pedido #" + order.getId() + " para 'SHIPPED'");
        Order updatedOrder = orderService.updateOrderStatus(order.getId(), "SHIPPED");
        
        if (updatedOrder != null) {
            logger.info("Status do pedido atualizado com sucesso!");
            logger.info("Um evento foi publicado e o serviço de email deve processar a notificação.");
            logger.info("Verifique os logs do EmailServiceApplication para confirmar o envio do email.");
        } else {
            logger.severe("Falha ao atualizar o status do pedido.");
        }
        
        logger.info("Demonstração concluída!");
    }
}
