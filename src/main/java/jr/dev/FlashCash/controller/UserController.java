package jr.dev.FlashCash.controller;

//import jakarta.validation.Valid;
import jr.dev.FlashCash.model.User;
import jr.dev.FlashCash.model.dto.SignUpForm;
import jr.dev.FlashCash.service.UserAccountService;
import jr.dev.FlashCash.service.UserService;
import jr.dev.FlashCash.service.form.AddLinkForm;
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

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signUpController(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute("user") SignUpForm form,
                                BindingResult result){
        if(result.hasErrors()){
            return "signup";
        }
        try{
            userService.registration(form);
        }
        catch(RuntimeException e){
            result.rejectValue("email", "error", "email already exists");
            return "signup";
        }
        return "redirect:/signin";
    }



}
