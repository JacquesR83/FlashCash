package jr.dev.FlashCash.service;

import jr.dev.FlashCash.model.Link;
import jr.dev.FlashCash.model.User;
import jr.dev.FlashCash.repository.LinkRepository;
import jr.dev.FlashCash.repository.UserRepository;
import jr.dev.FlashCash.service.form.AddLinkForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService {

    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final LinkRepository linkRepository;

    @Autowired
    public LinkService(UserRepository userRepository, SessionService sessionService, LinkRepository linkRepository) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
        this.linkRepository = linkRepository;
    }

    public List<Link> getLinksForUser(User user) {
        return linkRepository.findByUser1(user);
    }

    public void addlink(AddLinkForm form){
        User friend = userRepository
                .findUserByEmail(form.getEmail())
                .orElseThrow(()-> new RuntimeException("User with email " + form.getEmail() + " not found"));
        User connectedUser = sessionService.sessionUser();
        Link link = new Link();
        link.setUser1(connectedUser);
        link.setUser2(friend);
        linkRepository.save(link);
    }

    public List<String> findLinksEmail() {
        User connectedUser = sessionService.sessionUser();
        List<String> contactsEmail = userRepository.findEmailsByUser();

        return contactsEmail;
    }
}

