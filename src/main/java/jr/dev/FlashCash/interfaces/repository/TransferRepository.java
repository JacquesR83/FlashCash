package jr.dev.FlashCash.interfaces.repository;

import jr.dev.FlashCash.model.Transfer;
import jr.dev.FlashCash.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer> {
    List<Transfer> findTransfersByFrom(User user);
}
