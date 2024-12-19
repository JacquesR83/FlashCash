package jr.dev.FlashCash.controller;

//import jakarta.validation.Valid;
import jr.dev.FlashCash.model.User;
import jr.dev.FlashCash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    UserService userService;

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
    public String processSignUp(@ModelAttribute("user") User user,
                                BindingResult result){
        if(result.hasErrors()){
            return "signup";
        }
        try{
            userService.saveUser(user);
        }
        catch(RuntimeException e){
            result.rejectValue("email", "error", "email already exists");
            return "signup";
        }
        return "redirect:/signin";
    }
}
