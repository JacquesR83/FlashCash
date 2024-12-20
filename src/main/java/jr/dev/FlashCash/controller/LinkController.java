package jr.dev.FlashCash.controller;

import jr.dev.FlashCash.service.LinkService;
import jr.dev.FlashCash.service.form.AddLinkForm;
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
public class LinkController {

    private final LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    private static final Logger logger = LoggerFactory.getLogger(LinkController.class);

    @GetMapping("/add-link")
    public ModelAndView showLinkForm(Model model){
        logger.info("Show link form");
        return new ModelAndView("add-a-link", "addLinkForm", new AddLinkForm());
    }

    @PostMapping("/add-link")
    public ModelAndView processAddLinkForm(@ModelAttribute("addLinkForm") AddLinkForm form){
        logger.info("adding a link");
        linkService.addlink(form);
        return new ModelAndView("home");
    }

}
