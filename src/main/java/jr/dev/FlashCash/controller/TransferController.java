package jr.dev.FlashCash.controller;

import jakarta.validation.Valid;
import jr.dev.FlashCash.controller.exceptions.InsufficientFundsException;
import jr.dev.FlashCash.controller.exceptions.InvalidAccountException;
import jr.dev.FlashCash.model.Link;
import jr.dev.FlashCash.model.Transfer;
import jr.dev.FlashCash.model.User;
import jr.dev.FlashCash.service.LinkService;
import jr.dev.FlashCash.service.SessionService;
import jr.dev.FlashCash.service.TransferService;
import jr.dev.FlashCash.service.form.AddToFlashCashForm;
import jr.dev.FlashCash.service.form.CashToBankForm;
import jr.dev.FlashCash.service.form.TransferForm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class TransferController {

    private static final Logger logger = LoggerFactory.getLogger(TransferController.class);

    private final LinkService linkService;
    private final TransferService transferService;
    private final SessionService sessionService;

    @GetMapping ("/add-to-flashcash")
    public ModelAndView showCashForm(){
        logger.info("Cash adding display form");
        return new ModelAndView("add-to-flashcash", "addToFlashCashForm", new AddToFlashCashForm());
    }

    @PostMapping("/add-to-flashcash")
    public ModelAndView addCashToAccount(
            @ModelAttribute("addToFlashCashForm") AddToFlashCashForm form,
            BindingResult result)
    {
        logger.info("Adding cash");
        if(result.hasErrors()) {
            return new ModelAndView("add-to-flashcash", "addToFlashCashForm", form);
        }
        transferService.transferCashToAccount(form);
        User user = sessionService.sessionUser();
        return new ModelAndView ("account", "user", user);
    }
    @GetMapping ("/cash-to-bank")
    public ModelAndView showWithdrawCashForm(Model model){
        logger.info("Withdraw display form");
        String linkedIban = transferService.findIban();
        model.addAttribute("linkedIban", linkedIban);
        return new ModelAndView("cash-to-bank", "cashToBankForm", new CashToBankForm());
    }

    @PostMapping("/cash-to-bank")
    public ModelAndView sendCashToBankAccount(
            @ModelAttribute("cashToBankForm") @Valid CashToBankForm form,
            BindingResult result,
            Model model) {
        logger.info("Sending cash out");

        if(result.hasErrors()){
                return new ModelAndView ("cash-to-bank", "cashToBankForm", form);
        }
        if (form.getAmount() == null || form.getIban() == null) {
            model.addAttribute("errorMessage", "Amount and IBAN are required.");
            String linkedIban = transferService.findIban();
            model.addAttribute("linkedIban", linkedIban);
            return new ModelAndView("cash-to-bank", "cashToBankForm", form);
        }

        try {
            transferService.transferCashToBank(form);
        } catch (InsufficientFundsException ex) {
            return new ModelAndView ("cash-to-bank", "errorMessage", "Insufficient funds for transfer.");
        } catch (InvalidAccountException ex) {
            return new ModelAndView("cash-to-bank", "errorMessage", "Invalid IBAN provided.");
        } catch (RuntimeException ex) {
            return new ModelAndView("cash-to-bank", "errorMessage", "An error occurred: " + ex.getMessage());
        }

            User user = sessionService.sessionUser();
            List<Link> links = linkService.getLinksForUser(user);
            List<Transfer> transfers = transferService.findTransactions();

            model.addAttribute("transfers", transfers);
            model.addAttribute("links", links);
            model.addAttribute("user", user);

            return new ModelAndView("account");

    }

    @GetMapping("/transfer-to-contact")
    public ModelAndView showTransferToContactForm(Model model) {
        List<String> linksEmail = linkService.findLinksEmail();
        model.addAttribute("linksEmail", linksEmail);
        return new ModelAndView("transfer-to-contact", "transferForm", new TransferForm());
    }

    @PostMapping("/transfer-to-contact")
    public ModelAndView transferToContact(@ModelAttribute("transferForm") @Valid TransferForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<String> linksEmail = linkService.findLinksEmail();  // Example of adding the email list again
            return new ModelAndView("transfer-to-contact", "linksEmail", linksEmail);// Return to the form with error messages
        }

        if ("default".equals(form.getContactEmail())) {
            result.rejectValue("contactEmail", "error.contactEmail", "Please select a valid contact.");
            List<String> linksEmail = linkService.findLinksEmail();  // Example of adding the email list again
            return new ModelAndView("transfer-to-contact", "linksEmail", linksEmail);
        }

            transferService.transfer(form);
            List<Transfer> transfers = transferService.findTransactions();
            return new ModelAndView("transfers", "transfers",transfers);

    }

    @GetMapping("/transfers")
    public ModelAndView showTransfers(Model model) {
        List <Transfer> transfers = transferService.findTransactions();
        User user = sessionService.sessionUser();
        model.addAttribute("user", user);
        return new ModelAndView("transfers", "transfers", transfers);
    }


}
