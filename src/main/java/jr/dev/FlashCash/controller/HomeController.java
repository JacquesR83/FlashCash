package jr.dev.FlashCash.controller;

import jr.dev.FlashCash.model.Link;
import jr.dev.FlashCash.model.User;
import jr.dev.FlashCash.service.AuthenticationService;
import jr.dev.FlashCash.service.LinkService;
import jr.dev.FlashCash.service.SessionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private final SessionService sessionService;
    private final LinkService linkService;

    public HomeController(SessionService sessionService, LinkService linkService, AuthenticationService authenticationService) {
        this.sessionService = sessionService;
        this.linkService = linkService;
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
        model.addAttribute("user", user);
        model.addAttribute("links",links);
        return "account";
    }


}
