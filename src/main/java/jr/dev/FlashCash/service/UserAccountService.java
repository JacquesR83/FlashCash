package jr.dev.FlashCash.service;

import jr.dev.FlashCash.model.User;
import jr.dev.FlashCash.model.UserAccount;
import jr.dev.FlashCash.repository.UserAccountRepository;
import jr.dev.FlashCash.repository.UserRepository;
import jr.dev.FlashCash.service.form.AddIbanForm;
import jr.dev.FlashCash.service.form.AddToFlashCashForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserAccountService {

    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountService(UserRepository userRepository, SessionService sessionService, UserAccountRepository userAccountRepository) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
        this.userAccountRepository = userAccountRepository;
    }


    public void insertIban(AddIbanForm form) {
        if(form != null){
            userAccountRepository.save(sessionService.sessionUser().getAccount().getIban(form.getIban()));
        }
    }

}
