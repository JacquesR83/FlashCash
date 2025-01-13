package jr.dev.FlashCash.controller.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientFundsException.class)
    public String handleInsufficientFundsException(InsufficientFundsException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "cash-to-bank";
    }

    @ExceptionHandler(InvalidAccountException.class)
    public String handleInvalidAccountException(InvalidAccountException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "cash-to-bank";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleCashToBankRuntimeException(RuntimeException ex, Model model) {
        model.addAttribute("errorMessage", "An unexpected error occurred: " + ex.getMessage());
        return "cash-to-bank";
    }



    @ExceptionHandler(LinkAlreadyExistsException.class)
    public ModelAndView handleLinkAlreadyExistsException(LinkAlreadyExistsException ex) {
        ModelAndView modelAndView = new ModelAndView("add-a-link");
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(CannotAddSelfException.class)
    public ModelAndView handleCannotAddSelfException(CannotAddSelfException ex) {
        ModelAndView modelAndView = new ModelAndView("add-a-link");
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleLinkException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("add-a-link");
        modelAndView.addObject("errorMessage", "An unexpected error occurred: " + ex.getMessage());
        return modelAndView;
    }

}
