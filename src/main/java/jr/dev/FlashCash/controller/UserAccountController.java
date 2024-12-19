package jr.dev.FlashCash.controller;

import jr.dev.FlashCash.model.UserAccount;
import jr.dev.FlashCash.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserAccountController {

    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping ("/addcash")
    public String addAmount(@RequestParam double amount, Model model ){
        UserAccount updatedAccount = userAccountService.addingMoney(amount);
        model.addAttribute("account", updatedAccount);
        return "home";
    }

    @PostMapping ("/takecash")
    public String cutAmount(@RequestParam double amount, Model model ){
        UserAccount updatedAccount = userAccountService.withdrawMoney(amount);
        model.addAttribute("account", updatedAccount);
        return "home";
    }


}
