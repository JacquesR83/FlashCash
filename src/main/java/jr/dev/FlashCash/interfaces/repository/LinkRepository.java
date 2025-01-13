package jr.dev.FlashCash.interfaces.repository;

import jr.dev.FlashCash.model.Link;
import jr.dev.FlashCash.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface LinkRepository extends JpaRepository<Link, Integer> {

//    List<Link> findLinksByUser1Id(Integer id);

    // Using a stocked procedure
    @Query(value= "CALL PS_Links(:user1Id, :user2Id)", nativeQuery = true)
    List<Link> findLinkByUser1AndUser2StoredProcedure(@Param("user1Id") int user1Id, @Param("user2Id") int user2Id);


    @Query("SELECT l FROM Link l WHERE l.user1.id = :userId")
    List<Link> findLinksByUserId(@Param("userId") Integer userId);

    List<Link> findLinksByUser1(User totoUser);

    List<Link> findLinksByUser2(User totoUser);

    List<Link> findByUser1OrUser2(User user1, User user2);

}
