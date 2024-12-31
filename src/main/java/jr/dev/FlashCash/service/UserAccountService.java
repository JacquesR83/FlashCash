package jr.dev.FlashCash.service;

import jr.dev.FlashCash.repository.UserAccountRepository;
import jr.dev.FlashCash.repository.UserRepository;
import jr.dev.FlashCash.service.form.AddIbanForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserAccountService {

    private final SessionService sessionService;
    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountService(SessionService sessionService, UserAccountRepository userAccountRepository) {
        this.sessionService = sessionService;
        this.userAccountRepository = userAccountRepository;
    }


    public void insertIban(AddIbanForm form) {
        if(form != null){
            userAccountRepository.save(sessionService.sessionUser().getAccount().getIban(form.getIban()));
        }
    }

}
