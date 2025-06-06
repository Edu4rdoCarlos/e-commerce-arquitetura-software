package app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import controller.OrderController;
import controller.ProductController;
import domain.model.Product;
import domain.model.User;
import infra.DatabaseConnection;
import infra.DatabaseInitializer;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getInstance();
            DatabaseInitializer.initialize(conn);
            DatabaseInitializer.seed(conn);
            conn.close();

            logger.info("=== Produtos Disponíveis ===");
            ProductController pc = new ProductController();
            pc.listAllProducts();

            User user = new User(1L, "Dudu", "dudu@email.com");

            Product p1 = new Product(1L, "Notebook", 3500.00, 10);
            Product p2 = new Product(2L, "Smartphone", 2200.00, 5);
            List<Product> produtosSelecionados = Arrays.asList(p1, p2);

            logger.info("=== Criando Pedido ===");
            OrderController oc = new OrderController();
            oc.createOrder(user, produtosSelecionados);

            logger.info("=== Pedidos do Usuário ===");
            oc.listOrders(user);

            logger.info("=== Detalhes do Pedido #1 ===");
            oc.viewOrderDetails(1L);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
