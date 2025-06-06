package controller;

import java.util.List;

import domain.model.Product;
import service.ProductService;

public class ProductController {
    private ProductService productService = new ProductService();

    public void listAllProducts() {
        List<Product> products = productService.getAllProducts();
        System.out.println("Produtos disponíveis:");
        for (Product p : products) {
            System.out.println("- " + p.getName() + " (R$ " + p.getPrice() + ")");
        }
    }
}
