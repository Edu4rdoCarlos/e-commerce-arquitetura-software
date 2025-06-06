package filter;

import model.Product;
import java.util.List;

public class InventoryCheckFilter {
    public boolean apply(List<Product> products) {
        for (Product p : products) {
            if (p.getStock() <= 0) {
                System.out.println("Produto sem estoque: " + p.getName());
                return false;
            }
        }
        return true;
    }
}
