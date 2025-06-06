package pipeline;

import model.Cart;
import model.Product;

import java.util.List;


public class InventoryCheckFilter implements Filter<Cart, String> {

    @Override
    public String execute(Cart cart) throws Exception {
        System.out.println("🔍 Verificando estoque...");

        for (Product product : cart.getProducts()) {
            if (product.getStock() <= 0) {
                throw new Exception("Produto fora de estoque: " + product.getName());
            }
            return "✔ Produto OK: " + product.getName();
        }

        return null;
    }
}