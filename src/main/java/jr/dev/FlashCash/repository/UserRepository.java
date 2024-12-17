package jr.dev.FlashCash.repository;

import jr.dev.FlashCash.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Integer> {

    public User findByEmail(String email);
}
