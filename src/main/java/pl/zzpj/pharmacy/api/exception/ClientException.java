package pl.zzpj.pharmacy.api.exception;

public class ClientException extends RuntimeException {

    public ClientException(String message) {
        super(message);
    }
}