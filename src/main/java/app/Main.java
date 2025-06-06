package app;

import consumer.EmailConsumer;
import controller.OrderController;
import controller.ProductController;
import domain.model.Cart;
import domain.model.Product;
import domain.model.User;
import infra.DatabaseConnection;
import infra.DatabaseInitializer;
import service.AuthService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final Scanner scanner = new Scanner(System.in);
    private static final ProductController productController = new ProductController();
    private static final OrderController orderController = new OrderController();
    private static final AuthService authService = new AuthService();

    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getInstance();
            DatabaseInitializer.initialize(conn);
            DatabaseInitializer.seed(conn);
            conn.close();

            System.out.println("==== Login ====");
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Senha: ");
            String password = scanner.nextLine();

            User user = authService.authenticate(email, password);
            if (user == null) {
                System.out.println("Login inválido. Encerrando...");
                return;
            }

            System.out.printf("Bem-vindo, %s (%s)%n", user.getName(), user.getRole());

            while (true) {
                if ("admin".equalsIgnoreCase(user.getRole().toString())) {
                    System.out.println("\n=== Menu Administrador ===");
                    System.out.println("1. Monitorar pedidos realizados");
                    System.out.println("0. Sair");
                    System.out.print("Escolha: ");
                    int opc = scanner.nextInt();

                    switch (opc) {
                        case 1 -> {
                            System.out.println("Monitorando produto...");
                            EmailConsumer ec = new EmailConsumer();
                            ec.consume();
                        }

                        case 0 -> {
                            System.out.println("Encerrando...");
                            return;
                        }
                        default -> System.out.println("Opção inválida.");
                    }

                } else {
                    System.out.println("\n=== Menu Cliente ===");
                    System.out.println("1. Pesquisar produtos");
                    System.out.println("2. Realizar compra");
                    System.out.println("3. Acompanhar andamento das compras");
                    System.out.println("0. Sair");
                    System.out.print("Escolha: ");
                    int opc = scanner.nextInt();

                    switch (opc) {
                        case 1 -> productController.listAllProducts();

                        case 2 -> {
                            List<Product> produtosSelecionados = new ArrayList<>();
                            while (true) {
                                System.out.print("ID do produto (0 para finalizar): ");
                                Long prodId = scanner.nextLong();
                                if (prodId == 0) break;
                                System.out.print("Quantidade: ");
                                int qtd = scanner.nextInt();
                                Product p = productController.getProductById();
                                if (p != null) {
                                    produtosSelecionados.add(new Product(p.getId(), p.getName(), p.getPrice(), qtd));
                                } else {
                                    System.out.println("Produto não encontrado.");
                                }
                            }
                            Cart cart = new Cart(UUID.randomUUID().getMostSignificantBits(), user, produtosSelecionados);
                            orderController.createOrder(cart);
                        }

                        case 3 -> orderController.listOrders(user);

                        case 0 -> {
                            System.out.println("Encerrando...");
                            return;
                        }

                        default -> System.out.println("Opção inválida.");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
