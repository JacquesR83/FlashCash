package jr.dev.FlashCash.service;

import jr.dev.FlashCash.model.User;
import jr.dev.FlashCash.model.UserAccount;
import jr.dev.FlashCash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAccountService {

    private final UserRepository userRepository;
    private final SessionService sessionService;

    @Autowired
    public UserAccountService(UserRepository userRepository, SessionService sessionService) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
    }

    @Transactional
    public UserAccount addingMoney(double newAmount) {
        User user = sessionService.sessionUser();
        UserAccount userAccount = user.getAccount();
        userAccount.plus(newAmount);
        userRepository.save(user);
        return userAccount;
    }

    @Transactional
    public UserAccount withdrawMoney(double newAmount) {
        User user = sessionService.sessionUser();
        UserAccount userAccount = user.getAccount();
        userAccount.minus(newAmount);
        userRepository.save(user);
        return userAccount;
    }

}
