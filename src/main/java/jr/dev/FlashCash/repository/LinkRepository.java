package jr.dev.FlashCash.repository;

import jr.dev.FlashCash.model.Link;
import jr.dev.FlashCash.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Integer> {

    List<Link> findLinksByUser1(User user1);


}
