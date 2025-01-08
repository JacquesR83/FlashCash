package jr.dev.FlashCash.controller;

import jakarta.validation.Valid;
import jr.dev.FlashCash.model.Link;
import jr.dev.FlashCash.model.Transfer;
import jr.dev.FlashCash.model.User;
import jr.dev.FlashCash.service.LinkService;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class UserAccountController {

    private static final Logger logger = LoggerFactory.getLogger(UserAccountService.class);
    private final TransferService transferService;
    private final SessionService sessionService;
    private final LinkService linkService;
    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService, TransferService transferService,
                                 SessionService sessionService, LinkService linkService)
    {
        this.transferService = transferService;
        this.sessionService = sessionService;
        this.userAccountService = userAccountService;
        this.linkService = linkService;
    }

    @GetMapping ("/add-to-flashcash")
    public ModelAndView showCashForm(Model model){
        logger.info("Cash adding display form");
        return new ModelAndView("add-to-flashcash", "addToFlashCashForm", new AddToFlashCashForm());
    }

    @PostMapping("/add-to-flashcash")
    public ModelAndView addCashToAccount(@ModelAttribute("addToFlashCashForm") AddToFlashCashForm form,
                                         Model model) {
        logger.info("Adding cash");
            transferService.transferCashToAccount(form);
            User user = sessionService.sessionUser();
            model.addAttribute("user", user);
            return new ModelAndView ("account");
    }

    @GetMapping("/add-iban")
    public ModelAndView showIbanForm(){
        logger.info("Iban adding display form");
        return new ModelAndView("add-iban", "addIbanForm", new AddIbanForm());
    }

    @PostMapping("/add-iban")
    public ModelAndView saveIban(
            @Valid @ModelAttribute("addIbanForm") AddIbanForm form,
            BindingResult result,
            Model model){

        if(result.hasErrors()){
            return new ModelAndView("add-iban", "addIbanForm", form);
        }

        logger.info("setting IBAN");
        userAccountService.insertIban(form);
        User user = sessionService.sessionUser();
        List<Link> linksEmail = linkService.getLinksForUser(user);
        List<Transfer> transfers = transferService.findTransactions();
        model.addAttribute("user", user);
        model.addAttribute("links",linksEmail);
        model.addAttribute("transfers",transfers);
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



}
