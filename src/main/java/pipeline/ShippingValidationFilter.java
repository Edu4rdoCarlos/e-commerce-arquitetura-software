package pipeline;

import model.Cart;
import model.Product;

import java.util.List;

public class ShippingValidationFilter implements Filter<Cart, String> {
    @Override
    public String execute(Cart cart) throws Exception {
        if (cart.getShipping() <= 0.0) {
            throw new Exception("Frete não calculado corretamente.");
        }

        return "🚚 Frete validado com sucesso.";
    }
}
