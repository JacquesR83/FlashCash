package jr.dev.FlashCash.controller;

import jr.dev.FlashCash.model.Link;
import jr.dev.FlashCash.model.Transfer;
import jr.dev.FlashCash.model.User;
import jr.dev.FlashCash.service.LinkService;
import jr.dev.FlashCash.service.SessionService;
import jr.dev.FlashCash.service.TransferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class HomeController {

    private final SessionService sessionService;
    private final LinkService linkService;
    private final TransferService transferService;

    public HomeController(SessionService sessionService,
                          LinkService linkService,
                          TransferService transferService) {
        this.sessionService = sessionService;
        this.linkService = linkService;
        this.transferService = transferService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        User user = sessionService.sessionUser();
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("/account")
    public String getAccount(Model model) {
        User user = sessionService.sessionUser();
        List<Link> links = linkService.getLinksForUser(user);
        List <Transfer> transfers = transferService.findTransactions();
        model.addAttribute("user", user);
        model.addAttribute("links",links);
        model.addAttribute("transfers", transfers);
        return "account";
    }


}
