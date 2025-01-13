package jr.dev.FlashCash.controller.exceptions;

public class InvalidAccountException extends RuntimeException{
    public InvalidAccountException(String message) {
        super(message);
    }
}
