package jr.dev.FlashCash.service;

import jr.dev.FlashCash.repository.UserAccountRepository;
import jr.dev.FlashCash.service.form.AddToFlashCashForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    private final UserAccountRepository userAccountRepository;
    private final SessionService sessionService;

    @Autowired
    public TransferService(UserAccountRepository userAccountRepository, SessionService sessionService) {
        this.userAccountRepository = userAccountRepository;
        this.sessionService = sessionService;
    }

    public void transferCashToAccount(AddToFlashCashForm form) {
        if(form != null){
            userAccountRepository.save(sessionService.sessionUser().getAccount().plus(form.getAmount()));
        }
    }
}
