package jr.dev.FlashCash.service;

import jr.dev.FlashCash.controller.exceptions.CannotAddSelfException;
import jr.dev.FlashCash.controller.exceptions.LinkAlreadyExistsException;
import jr.dev.FlashCash.controller.exceptions.UserNotFoundException;
import jr.dev.FlashCash.model.Link;
import jr.dev.FlashCash.model.User;
import jr.dev.FlashCash.interfaces.repository.LinkRepository;
import jr.dev.FlashCash.interfaces.repository.UserRepository;
import jr.dev.FlashCash.service.form.AddLinkForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final LinkRepository linkRepository;

//    public List<Link> getLinksForUser(User user) {
//        return linkRepository.findLinksByUser1(user);
//    }
//    public List<Link> getLinksForUser(User user) {
//        return linkRepository.findLinksByUser1StoredProcedure(user);
//    }

    public List<Link> getLinksForUser(User user) {
        return linkRepository.findLinksByUserId(user.getId());
    }

    public void addLink(AddLinkForm form){

        if (form.getEmail().equals(sessionService.sessionUser().getEmail())) {
            throw new CannotAddSelfException("You cannot add yourself as a friend.");
        }

//        if(userRepository.findUserByEmail(form.getEmail()).isEmpty()){
//            throw new RuntimeException("Invalid email, cannot add link");
//        }
        User friend = userRepository.findUserByEmail(form.getEmail())
                .orElseThrow(() -> new UserNotFoundException("The user you are trying to add does not exist."));

        User connectedUser = sessionService.sessionUser();

        List<Link> existingLink = linkRepository.findLinkByUser1AndUser2StoredProcedure(connectedUser.getId(),friend.getId());


        if(!existingLink.isEmpty()){
            throw new LinkAlreadyExistsException("Link already exists");
        }

        Link link = new Link();
        link.setUser1(connectedUser);
        link.setUser2(friend);

        linkRepository.save(link);

    }

    public List<String> findLinksEmail() {
        // Collects all links with current connected user
        List<Link> links = linkRepository.findLinksByUserId(sessionService.sessionUser().getId());

        //    // Alternatives find links emails

        // List<Link> links = linkRepository.findLinksByUser1(sessionService.sessionUser());

//    public List<String> findLinksEmail() {
//        // Collects all links with current connected user
////        List<Link> links = linkRepository.findLinksByUser1(sessionService.sessionUser());
//        List<String> emails = userRepository.findLinks(sessionService.sessionUser().getId());
//        return emails;
//    }

        // For each link : get user2 email
        return links.stream()
                .map(link -> link.getUser2().getEmail())
                .collect(Collectors.toList());
    }


    public void removeLink(Integer friendId) {
        User connectedUser = sessionService.sessionUser();

        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("User with id " + friendId + " not found"));

        // Check if link exists between 2 users
        List<Link> existingLinks = linkRepository.findLinkByUser1AndUser2StoredProcedure(connectedUser.getId(), friend.getId());

        if (existingLinks.isEmpty()) {
            throw new RuntimeException("No link found between these users.");
        }


        Link link = existingLinks.get(0); // Only 1 link can exist between 2 users, get 1st one
        linkRepository.delete(link);
    }




}

