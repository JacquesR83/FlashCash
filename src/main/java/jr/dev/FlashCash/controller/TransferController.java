package jr.dev.FlashCash.controller;

import jr.dev.FlashCash.model.Transfer;
import jr.dev.FlashCash.service.LinkService;
import jr.dev.FlashCash.service.TransferService;
import jr.dev.FlashCash.service.form.TransferForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TransferController {

    private final LinkService linkService;
    private final TransferService transferService;

    public TransferController(LinkService linkService, TransferService transferService) {
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
    public ModelAndView transferToContact(Model model, @ModelAttribute("transferForm") TransferForm form) {
        transferService.transfer(form);
        List<Transfer> transfers = transferService.findTransactions();
        model.addAttribute("transfers", transfers);
        return new ModelAndView("transfers");
    }


    @GetMapping("/transfers")
    public ModelAndView showTransfers(Model model) {
        model.addAttribute("transfers");
        return new ModelAndView("transfers");
    }
}
