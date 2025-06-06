package pipeline;

import domain.model.Product;

public class InventoryCheckFilter implements Filter {

    @Override
    public void execute(OrderContext context) throws Exception {
        System.out.println("🔍 Verificando estoque...");

        for (Product product : context.products) {
            if (product.getStock() <= 0) {
                throw new Exception("Produto fora de estoque: " + product.getName());
            }
            System.out.println("✔ Produto OK: " + product.getName());
        }
    }
}