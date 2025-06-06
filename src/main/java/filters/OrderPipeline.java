package filters;

import java.util.ArrayList;
import java.util.List;

import domain.model.Cart;

public class OrderPipeline {

    private final List<Filter<OrderContext, String>> filters = new ArrayList<>();

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void execute(Cart cart) throws Exception {
        for (Filter filter : filters) {
            System.out.println(filter.execute(cart));
        }
    }
}
