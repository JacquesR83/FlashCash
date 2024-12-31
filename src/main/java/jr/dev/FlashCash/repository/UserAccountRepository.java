package jr.dev.FlashCash.repository;

import jr.dev.FlashCash.model.User;
import jr.dev.FlashCash.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Integer> {

    @Query("SELECT a FROM UserAccount a WHERE a.accountId = :id")
    UserAccount findAccountByUserId (Integer id);

}
