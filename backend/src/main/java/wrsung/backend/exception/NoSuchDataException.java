package wrsung.backend.exception;

public class NoSuchDataException extends RuntimeException {
    public NoSuchDataException(String message) {
        super(message);
    }
}
