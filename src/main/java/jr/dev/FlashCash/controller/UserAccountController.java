package jr.dev.FlashCash.controller;

import jr.dev.FlashCash.model.User;
import jr.dev.FlashCash.service.SessionService;
import jr.dev.FlashCash.service.TransferService;
import jr.dev.FlashCash.service.UserAccountService;
import jr.dev.FlashCash.service.form.AddIbanForm;
import jr.dev.FlashCash.service.form.AddToFlashCashForm;
import jr.dev.FlashCash.service.form.CashToBankForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class UserAccountController {

    private static final Logger logger = LoggerFactory.getLogger(UserAccountService.class);
    private final TransferService transferService;
    private final SessionService sessionService;
    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService,TransferService transferService,
                                 SessionService sessionService)
    {
        this.transferService = transferService;
        this.sessionService = sessionService;
        this.userAccountService = userAccountService;
    }

    @GetMapping ("/add-to-flashcash")
    public ModelAndView showCashForm(Model model){
        logger.info("Cash adding display form");
        return new ModelAndView("add-to-flashcash", "addToFlashCashForm", new AddToFlashCashForm());
    }

    @PostMapping("/add-to-flashcash")
    public ModelAndView addCashToAccount(Model model, @ModelAttribute("addToFlashCashForm") AddToFlashCashForm form) {
        logger.info("Adding cash");
            transferService.transferCashToAccount(form);
            User user = sessionService.sessionUser();
            model.addAttribute("user", user);
            return new ModelAndView ("account");
    }

    @GetMapping("/add-iban")
    public ModelAndView showIbanForm(Model model){
        logger.info("Iban adding display form");
        return new ModelAndView("add-iban", "addIbanForm", new AddIbanForm());
    }

    @PostMapping("/add-iban")
    public ModelAndView saveIban(Model model, @ModelAttribute("addIbanForm") AddIbanForm form){
        logger.info("setting IBAN");
        userAccountService.insertIban(form);
        User user = sessionService.sessionUser();
        model.addAttribute("user", user);
        return new ModelAndView("account");
    }

    @GetMapping ("/cash-to-bank")
    public ModelAndView showWithdrawCashForm(Model model){
        logger.info("Withdraw display form");
        String linkedIban = transferService.findIban();
        model.addAttribute("linkedIban", linkedIban);
        return new ModelAndView("cash-to-bank", "cashToBankForm", new CashToBankForm());
    }

    @PostMapping("/cash-to-bank")
    public ModelAndView sendCashToBankAccount(Model model, @ModelAttribute("cashToBankForm") CashToBankForm form) {
        logger.info("Sending cash out");


        String linkedIban = transferService.findIban();
        model.addAttribute("linkedIban", linkedIban);

        User user = sessionService.sessionUser();
        model.addAttribute("user", user);
        transferService.transferCashToBank(form);

        return new ModelAndView ("account");
    }

//    @PostMapping ("/takecash")
//    public String cutAmount(@RequestParam double amount, Model model ){
//        UserAccount updatedAccount = userAccountService.withdrawMoney(amount);
//        model.addAttribute("account", updatedAccount);
//        return "home";
//    }


}
