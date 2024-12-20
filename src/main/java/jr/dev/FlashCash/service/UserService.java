package jr.dev.FlashCash.service;

import jr.dev.FlashCash.model.User;
import jr.dev.FlashCash.model.UserAccount;
import jr.dev.FlashCash.model.dto.SignUpForm;
import jr.dev.FlashCash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registration(SignUpForm form){
            User user = new User();
            UserAccount account = new UserAccount();
            account.setAmount(0.0);
            user.setAccount(account);
            user.setFirstname(form.getFirstname());
            user.setLastname(form.getLastname());
            user.setEmail(form.getEmail());
            user.setPassword(passwordEncoder.encode(form.getPassword()));
            return userRepository.save(user);
        }
    }

