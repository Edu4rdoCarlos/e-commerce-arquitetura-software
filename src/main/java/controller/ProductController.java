package controller;

import java.util.List;
import java.util.Scanner;

import domain.model.Product;
import service.ProductService;

public class ProductController {
    private ProductService productService = new ProductService();
    private final Scanner scanner = new Scanner(System.in);

    public void listAllProducts() {
        List<Product> products = productService.getAllProducts();
        System.out.println("Produtos disponíveis:");
        for (Product p : products) {
            System.out.println(p.getId() + "- " + p.getName() + " (R$ " + p.getPrice() + ")");
        }
    }

    public Product getProductById() {
        System.out.print("Digite o ID do produto: ");
        Long id = scanner.nextLong();

        Product product = productService.getById(id);

        if (product != null) {
            System.out.println("Produto selecionado:");
            System.out.println("- " + product.getName() + " (R$ " + product.getPrice() + ")");
            return product;
        } else {
            System.out.println("Produto com ID " + id + " não encontrado.");
            return null;
        }
    }




}
