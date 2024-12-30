package jr.dev.FlashCash.controller;

import jr.dev.FlashCash.service.LinkService;
import jr.dev.FlashCash.service.form.TransferForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TransferController {

    private final LinkService linkService;

    public TransferController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/transfer-to-contact")
    public ModelAndView transferToContact(Model model) {
        List<String> linksEmail = linkService.findLinksEmail();
        model.addAttribute("linksEmail", linksEmail);
        return new ModelAndView("transfer-to-contact", "transferForm", new TransferForm());
    }
}
