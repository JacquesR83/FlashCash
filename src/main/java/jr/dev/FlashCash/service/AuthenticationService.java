package jr.dev.FlashCash.service;

import jr.dev.FlashCash.model.User;
import jr.dev.FlashCash.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service // Authentication procedure
public class AuthenticationService  implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override // overriding UserDetailsService (interface) method "loadUserByUsername"
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // We load our user from database => call repository to compare entry data with
        Optional<User> user = userRepository.findUserByEmail(s);
        if (user.isPresent()) {
            return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), new ArrayList<>());
        }
            throw new UsernameNotFoundException(s);
    }
}
