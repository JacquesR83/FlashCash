package jr.dev.FlashCash.repository;

import jr.dev.FlashCash.model.Link;
import jr.dev.FlashCash.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LinkRepository extends JpaRepository<Link, Integer> {

//    List<Link> findLinksByUser1(User user1);

    // Using a stocked procedure
    @Query(value= "CALL PS_Links(:userId)", nativeQuery = true)
    List<Link> findLinksByUser1StoredProcedure(@Param("userId") int userId);


}
