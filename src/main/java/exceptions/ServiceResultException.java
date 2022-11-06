package exceptions;

public class ServiceResultException extends RuntimeException {
    public ServiceResultException(String textError) {
        super(textError);
    }
}
