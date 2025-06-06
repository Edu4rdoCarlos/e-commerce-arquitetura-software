package pipeline;

import java.util.ArrayList;
import java.util.List;

public class OrderPipeline {

    private final List<Filter> filters = new ArrayList<>();

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void execute(OrderContext context) throws Exception {
        for (Filter filter : filters) {
            filter.execute(context);
        }
    }
}
