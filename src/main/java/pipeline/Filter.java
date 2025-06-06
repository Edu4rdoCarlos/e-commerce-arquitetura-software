package pipeline;

public interface Filter {
    void execute(OrderContext context) throws Exception;
}