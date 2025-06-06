package filter;

import java.util.List;

import domain.model.Product;

public class ShippingCalculationFilter {
    public double apply(List<Product> products) {
        double totalWeight = products.size() * 2.0; // Simulação: 2kg por produto
        double cost = 10.0 + (totalWeight * 1.5);   // Frete base + custo por peso
        System.out.println("Frete calculado: R$" + cost);
        return cost;
    }
}
