package jr.dev.FlashCash.controller.exceptions;

public class CannotAddSelfException extends RuntimeException {
    public CannotAddSelfException(String message) {
        super(message);
    }
}