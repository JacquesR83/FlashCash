package jr.dev.FlashCash.service;

import jr.dev.FlashCash.interfaces.repository.UserAccountRepository;
import jr.dev.FlashCash.service.form.AddIbanForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final SessionService sessionService;
    private final UserAccountRepository userAccountRepository;

    public void insertIban(AddIbanForm form) {
        if(form != null){
            userAccountRepository.save(sessionService.sessionUser().getAccount().getIban(form.getIban()));
        }
    }

}
