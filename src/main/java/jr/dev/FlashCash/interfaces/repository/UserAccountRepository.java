package jr.dev.FlashCash.interfaces.repository;

import jr.dev.FlashCash.model.UserAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Integer> {

    @Query("SELECT a FROM UserAccount a WHERE a.accountId = :id")
    UserAccount findAccountByUserId (Integer id);

}
