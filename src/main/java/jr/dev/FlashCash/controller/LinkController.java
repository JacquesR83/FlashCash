package jr.dev.FlashCash.controller;

import jakarta.validation.Valid;
import jr.dev.FlashCash.controller.exceptions.CannotAddSelfException;
import jr.dev.FlashCash.controller.exceptions.LinkAlreadyExistsException;
import jr.dev.FlashCash.service.LinkService;
import jr.dev.FlashCash.service.form.AddLinkForm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


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
                                           BindingResult result){
        ModelAndView modelAndView = new ModelAndView("add-a-link", "addLinkForm", form);

        if(result.hasErrors()){
            return modelAndView;
        }

        try{
            logger.info("adding a link");
            linkService.addLink(form);
            modelAndView.setViewName("home");
        }
        catch (LinkAlreadyExistsException | CannotAddSelfException ex) {
            modelAndView.addObject("errorMessage", ex.getMessage());
        }
        catch (RuntimeException ex) {
            modelAndView.addObject("errorMessage", "An error occurred: " + ex.getMessage());
        }

        return modelAndView;
    }


    @PostMapping("/remove-link/{friendId}")
    public ModelAndView removeLink(@PathVariable Integer friendId) {
        ModelAndView modelAndView = new ModelAndView("home");

        try {
            linkService.removeLink(friendId);
            modelAndView.addObject("successMessage", "Friend removed successfully.");
        } catch (RuntimeException ex) {
            modelAndView.addObject("errorMessage", ex.getMessage());
        }

        return modelAndView;
    }

}
