package jr.dev.FlashCash.controller;

import jakarta.validation.Valid;
import jr.dev.FlashCash.controller.exceptions.CannotAddSelfException;
import jr.dev.FlashCash.controller.exceptions.LinkAlreadyExistsException;
import jr.dev.FlashCash.controller.exceptions.UserNotFoundException;
import jr.dev.FlashCash.service.LinkService;
import jr.dev.FlashCash.service.form.AddLinkForm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Binding;


@Controller
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;
    private static final Logger logger = LoggerFactory.getLogger(LinkController.class);

    @GetMapping("/add-link")
    public ModelAndView showLinkForm(){
        logger.info("Show link form");
        return new ModelAndView("add-a-link", "addLinkForm", new AddLinkForm());
    }

    @PostMapping("/add-link")
    public ModelAndView processAddLinkForm(@Valid @ModelAttribute("addLinkForm") AddLinkForm form,
                                           BindingResult result, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("add-a-link", "addLinkForm", form);

        if(result.hasErrors()){
            return modelAndView;
        }

        try{
            logger.info("adding a link");
            linkService.addLink(form);

            redirectAttributes.addFlashAttribute("successMessage", "Friend added successfully.");

            return new ModelAndView("redirect:/account");
        }
        catch (LinkAlreadyExistsException | CannotAddSelfException| UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return new ModelAndView("redirect:/add-link");
        }
        catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred: " + ex.getMessage());
            return new ModelAndView("redirect:/add-link");
        }
    }


    @PostMapping("/remove-link/{friendId}")
    public ModelAndView removeLink(@PathVariable Integer friendId, RedirectAttributes redirectAttributes) {
        try {
            linkService.removeLink(friendId);
            redirectAttributes.addFlashAttribute("successMessage", "Friend removed successfully.");
            return new ModelAndView("redirect:/account");
        } catch (RuntimeException ex) {
            ModelAndView modelAndView = new ModelAndView("account");
            modelAndView.addObject("errorMessage", ex.getMessage());
            return modelAndView;
        }
    }

}

