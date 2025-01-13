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
    private final LinkRepository linkRepository;

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


//    @Transactional
//    public void deleteTotoUsers() {
//        // Récupérer les utilisateurs avec le prénom "toto"
//        List<User> totoUsers = userRepository.findByFirstname("toto");
//
//        // Supprimer les liens où Toto apparaît comme user1 ou user2
//        for (User totoUser : totoUsers) {
//            // Trouver les liens où Toto est user1 ou user2
//            List<Link> linksAsUser1 = linkRepository.findLinksByUser1(totoUser);  // Liens où Toto est user1
//            List<Link> linksAsUser2 = linkRepository.findLinksByUser2(totoUser);  // Liens où Toto est user2
//
//            // Combiner les deux listes de liens
//            linksAsUser1.addAll(linksAsUser2);
//
//            // Supprimer tous les liens associés à Toto
//            linkRepository.deleteAll(linksAsUser1);
//        }
//
//        // Supprimer les utilisateurs "Toto"
//        for (User totoUser : totoUsers) {
//            userRepository.delete(totoUser);
//        }
//
//        // Maintenant, supprimer tous les liens pour les autres utilisateurs liés à Toto
//        for (User totoUser : totoUsers) {
//            // Supprimer tous les liens où Toto était impliqué (même en tant que user2)
//            List<User> usersLinkedToToto = userRepository.findUsersLinkedToToto(totoUser);
//            for (User user : usersLinkedToToto) {
//                List<Link> linksToDelete = linkRepository.findByUser1OrUser2(user, totoUser);
//                linkRepository.deleteAll(linksToDelete);
//            }
//        }
//    }


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

