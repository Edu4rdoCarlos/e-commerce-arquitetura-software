package service;

import java.util.List;

import domain.model.Product;
import repository.ProductRepository;

public class ProductService {

    private ProductRepository productRepository = new ProductRepository();

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getById(Long productId) {
        return productRepository.findById(productId);
    }

}
