package jr.dev.FlashCash.controller.exceptions;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);  // Passer le message à la superclasse (RuntimeException)
    }
}
