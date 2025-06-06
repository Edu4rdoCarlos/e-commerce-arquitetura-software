package controller;

import model.Product;
import service.ProductService;
import java.util.List;

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
