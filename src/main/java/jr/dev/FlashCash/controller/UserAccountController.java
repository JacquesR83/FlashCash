package jr.dev.FlashCash.controller;

import jr.dev.FlashCash.model.UserAccount;
import jr.dev.FlashCash.repository.UserAccountRepository;
import jr.dev.FlashCash.repository.UserRepository;
import jr.dev.FlashCash.service.UserAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserAccountController {

    private final UserAccountService userAccountService;
    private static final Logger logger = LoggerFactory.getLogger(UserAccountService.class);
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountController(UserAccountService userAccountService, UserRepository userRepository, UserAccountRepository userAccountRepository) {
        this.userAccountService = userAccountService;
        this.userRepository = userRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @GetMapping ("/addcash")
    public String showCashForm(Model model){
        logger.info("Affichage du formulaire d'ajout de cash");
        return "addmoney";
    }

    @PostMapping("/addcash/validate")
    public String addAmount(@RequestParam double amount, BindingResult result, Model model ) {
        logger.info("Procédure de validation d'ajout des users");
        if(!result.hasErrors()){
            UserAccount updatedAccount = userAccountService.addingMoney(amount);
            userAccountRepository.save(updatedAccount);
            model.addAttribute("account", updatedAccount);

            return"/home";
        }
        return "addmoney";
    }

    @PostMapping ("/takecash")
    public String cutAmount(@RequestParam double amount, Model model ){
        UserAccount updatedAccount = userAccountService.withdrawMoney(amount);
        model.addAttribute("account", updatedAccount);
        return "home";
    }


}
