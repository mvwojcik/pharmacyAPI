package pl.zzpj.pharmacy.api.exception;

public class OrderException extends RuntimeException {
    public OrderException(String message) {
        super(message);
    }
}
