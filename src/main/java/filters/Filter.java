package filters;

public interface Filter<I, O> {
    O execute(I context) throws Exception;
}