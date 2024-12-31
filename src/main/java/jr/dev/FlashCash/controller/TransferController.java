package jr.dev.FlashCash.controller;

import jakarta.validation.Valid;
import jr.dev.FlashCash.model.Transfer;
import jr.dev.FlashCash.model.User;
import jr.dev.FlashCash.repository.TransferRepository;
import jr.dev.FlashCash.repository.UserRepository;
import jr.dev.FlashCash.service.LinkService;
import jr.dev.FlashCash.service.SessionService;
import jr.dev.FlashCash.service.TransferService;
import jr.dev.FlashCash.service.form.TransferForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TransferController {

    private final LinkService linkService;
    private final TransferService transferService;


    public TransferController(LinkService linkService, TransferService transferService, SessionService sessionService, UserRepository userRepository) {
        this.linkService = linkService;
        this.transferService = transferService;
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
            model.addAttribute("linksEmail", linksEmail);
            return new ModelAndView ("transfer-to-contact");  // Return to the form with error messages
        }

        if ("default".equals(form.getContactEmail())) {
            result.rejectValue("contactEmail", "error.contactEmail", "Please select a valid contact.");
            List<String> linksEmail = linkService.findLinksEmail();  // Example of adding the email list again
            model.addAttribute("linksEmail", linksEmail);
            return new ModelAndView("transfer-to-contact");
        }

        transferService.transfer(form);
        List<Transfer> transfers = transferService.findTransactions();
        model.addAttribute("transfers", transfers);
        return new ModelAndView("transfers");
    }


    @GetMapping("/transfers")
    public ModelAndView showTransfers(Model model) {
        List <Transfer> transfers = transferService.findTransactions();
        model.addAttribute("transfers", transfers);
        return new ModelAndView("transfers");
    }
}
