package service;

import model.Order;
import java.util.logging.Logger;

public class EmailService {
    private static final Logger logger = Logger.getLogger(EmailService.class.getName());

    public void sendEmail(String to, String subject, String body) {
        logger.info("Enviando email para: " + to);
        logger.info("Assunto: " + subject);
        logger.info("Conteúdo: " + body);
    }

    public void sendOrderConfirmationEmail(Order order) {
        String to = order.getUser().getEmail();
        String subject = "Confirmação de Pedido #" + order.getId();
        String body = buildOrderConfirmationEmailBody(order);
        
        sendEmail(to, subject, body);
    }

    public void sendOrderStatusUpdateEmail(Order order) {
        String to = order.getUser().getEmail();
        String subject = "Atualização do Pedido #" + order.getId();
        String body = buildOrderStatusUpdateEmailBody(order);
        
        sendEmail(to, subject, body);
    }

    private String buildOrderConfirmationEmailBody(Order order) {
        StringBuilder body = new StringBuilder();
        body.append("Olá ").append(order.getUser().getName()).append(",\n\n");
        body.append("Seu pedido #").append(order.getId()).append(" foi recebido com sucesso!\n\n");
        body.append("Detalhes do pedido:\n");
        body.append("- Total: R$ ").append(String.format("%.2f", order.getTotal())).append("\n");
        body.append("- Status: ").append(order.getStatus()).append("\n");
        body.append("Obrigado por comprar conosco!\n");
        body.append("Equipe E-Commerce");
        
        return body.toString();
    }

    private String buildOrderStatusUpdateEmailBody(Order order) {
        StringBuilder body = new StringBuilder();
        body.append("Olá ").append(order.getUser().getName()).append(",\n\n");
        body.append("O status do seu pedido #").append(order.getId()).append(" foi atualizado para: ")
            .append(order.getStatus()).append("\n\n");
        
        switch (order.getStatus().toUpperCase()) {
            case "PROCESSING":
                body.append("Seu pedido está sendo processado e em breve será enviado.\n");
                break;
            case "SHIPPED":
                body.append("Seu pedido foi enviado e está a caminho!\n");
                break;
            case "DELIVERED":
                body.append("Seu pedido foi entregue. Esperamos que esteja satisfeito com sua compra!\n");
                break;
            case "CANCELLED":
                body.append("Seu pedido foi cancelado conforme solicitado.\n");
                break;
            default:
                body.append("Acompanhe o status do seu pedido em nossa plataforma.\n");
        }
        
        body.append("\nPara mais detalhes, acesse sua conta em nosso site.\n\n");
        body.append("Atenciosamente,\n");
        body.append("Equipe E-Commerce");
        
        return body.toString();
    }
}
