package jr.dev.FlashCash.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jr.dev.FlashCash.interfaces.repository.LinkRepository;
import jr.dev.FlashCash.model.Link;
import jr.dev.FlashCash.model.User;
import jr.dev.FlashCash.model.UserAccount;
import jr.dev.FlashCash.model.dto.SignUpForm;
import jr.dev.FlashCash.interfaces.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registration(SignUpForm form){

        if(userRepository.existsByEmail(form.getEmail())){
            throw new RuntimeException("Email already in use");
        };
//        if(userRepository.findUserByEmail(form.getEmail()).isPresent()){
//            throw new RuntimeException("User already exists");
//        }
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


//
//    @Transactional
//    public boolean isAuthenticated(@NotBlank(message = "Email needed") @Email(message = "Invalid email") String email, @NotBlank(message = "Password needed") String password) {
//        // Use Entity Manager as for Stocked Procedure and get encrypted password from DB in 2 requests (JPA working way)
//        entityManager.createNativeQuery("CALL PS_Password(:email, @userPassword)")
//                        .setParameter("email",email)
//                        .executeUpdate();
//        Object result = entityManager.createNativeQuery("SELECT @userPassword")
//                        .getSingleResult();
//
//        String storedPassword = result.toString();
//
//        if (storedPassword != null && passwordEncoder.matches(password, storedPassword)) {
//            // Flush session variable
//            entityManager.createNativeQuery("SET @userPassword = NULL").executeUpdate();
//            return true;  // Si les mots de passe correspondent
//        }
//        // Flush session variable
//        entityManager.createNativeQuery("SET @userPassword = NULL").executeUpdate();
//        return false;
//    }
//
//    public Optional<User> getUserByEmail(@NotBlank(message = "Email needed") @Email(message = "Invalid email") String email) {
//         return Optional.ofNullable(userRepository.findUserByEmail(email))
//                 .orElseThrow(()-> new UsernameNotFoundException("User with email " + email +" not found"));
//    }
}

