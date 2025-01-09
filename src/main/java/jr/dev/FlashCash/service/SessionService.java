package jr.dev.FlashCash.service;

import jr.dev.FlashCash.model.User;
import jr.dev.FlashCash.interfaces.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final UserRepository userRepository;

    public SessionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User sessionUser() {
        org.springframework.security.core.userdetails.User springUser =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findUserByEmail(springUser.getUsername())
                .orElseThrow(() -> new RuntimeException("User with this email cannot be found"));
    }
}
