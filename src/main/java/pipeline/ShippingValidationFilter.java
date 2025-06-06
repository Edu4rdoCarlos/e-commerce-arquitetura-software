package pipeline;

public class ShippingValidationFilter implements Filter {
    @Override
    public void execute(OrderContext context) throws Exception {
        if (context.order.getShipping() <= 0.0 && context.total < 500) {
            throw new Exception("Frete não calculado corretamente para pedidos abaixo de R$500.");
        }

        System.out.println("🚚 Frete validado com sucesso.");
    }
}
