package jr.dev.FlashCash.controller;

import jakarta.validation.Valid;
import jr.dev.FlashCash.interfaces.repository.UserRepository;
import jr.dev.FlashCash.model.User;
import jr.dev.FlashCash.model.dto.SignInForm;
import jr.dev.FlashCash.model.dto.SignUpForm;
import jr.dev.FlashCash.service.SessionService;
import jr.dev.FlashCash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public ModelAndView showSignUpView() {
        return new ModelAndView("signup", "signUpForm", new SignUpForm());
    }

    @PostMapping("/signup")
    public ModelAndView processSignUp(@Valid @ModelAttribute("signUpForm") SignUpForm form,
                                BindingResult result, RedirectAttributes redirectAttributes){

        if(result.hasErrors()){
            return new ModelAndView("signup", "signUpForm", form);
        }
        try{
            userService.registration(form);
        }
        catch(RuntimeException e){
            // Redirects an attribute => error to display it => from sign up page to the signin page
            redirectAttributes.addFlashAttribute("emailError","Email already exists");
            return new ModelAndView( "redirect:/signin");
        }
        return new ModelAndView("signin", "signUpForm", form);
    }

    @GetMapping("/signin")
    public ModelAndView showSignInView() {
        return new ModelAndView ("signin", "signInForm", new SignInForm());
    }

    @PostMapping("/authentication")
    public ModelAndView processSignIn(@Valid @ModelAttribute("signInForm") SignInForm form,
                                      BindingResult result){
        if(result.hasErrors()){
             return new ModelAndView ("signin", "signInForm", form);
        }

        Optional<User> optionalUser = userService.getUserByEmail(form.getEmail());

        if(!optionalUser.isPresent()){
            return new ModelAndView("signin","signInForm",form)
                    .addObject("authError","Email not found");
        }

        boolean isAuthenticated = userService.isAuthenticated(form.getEmail(), form.getPassword());

        if(isAuthenticated){
            User user = optionalUser.get();

            ModelAndView modelAndView = new ModelAndView("home");
            // Add user to model (for Vue)
            modelAndView.addObject("user", user);
            return modelAndView;

        } else {
            return new ModelAndView("signin", "signInForm", form)
            .addObject("authError","Invalid password");
        }


    }


    @GetMapping("/logout")
    public ModelAndView showLogout() {
        return new ModelAndView ("logout");
    }


}
