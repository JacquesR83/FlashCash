package jr.dev.FlashCash.service;

import jr.dev.FlashCash.model.Transfer;
import jr.dev.FlashCash.model.User;
import jr.dev.FlashCash.repository.TransferRepository;
import jr.dev.FlashCash.repository.UserAccountRepository;
import jr.dev.FlashCash.repository.UserRepository;
import jr.dev.FlashCash.service.form.AddToFlashCashForm;
import jr.dev.FlashCash.service.form.TransferForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransferService {

    private final UserAccountRepository userAccountRepository;
    private final SessionService sessionService;
    private final TransferRepository transferRepository;
    private final UserRepository userRepository;

    private final LocalDateTime dateOfTransfer = LocalDateTime.now();



    @Autowired
    public TransferService(UserAccountRepository userAccountRepository, SessionService sessionService,
                           TransferRepository transferRepository, UserRepository userRepository) {
        this.userAccountRepository = userAccountRepository;
        this.sessionService = sessionService;
        this.transferRepository = transferRepository;
        this.userRepository = userRepository;
    }

    public void transferCashToAccount(AddToFlashCashForm form) {
        if(form != null){
            userAccountRepository.save(sessionService.sessionUser().getAccount().plus(form.getAmount()));
        }
    }

    public void transfer(TransferForm form) {
        // Update Sender & Receiver accounts amounts
        userAccountRepository.save(sessionService.sessionUser().getAccount().minus(form.getAmount()));
        Optional<User> receiveUser = userRepository.findUserByEmail(form.getContactEmail());
        if(receiveUser.isPresent()){
            User receiver = receiveUser.get();
            userAccountRepository.save(receiver.getAccount().plus(form.getAmount()));
        }

        // fill transfer with information
        Transfer currentTransfer = new Transfer();
        currentTransfer.setFrom(sessionService.sessionUser()); // Gets whole user
        currentTransfer.setTo(receiveUser.get()); // Gets whole user
        currentTransfer.setDateTime(dateOfTransfer); // To clean
        currentTransfer.setDescription(form.getDescription());
        currentTransfer.setAmountBeforeFee(form.getAmount());
        currentTransfer.setAmountAfterFee(applyFee(form.getAmount()));

        // save in repo
        transferRepository.save(currentTransfer);

    }

    public List<Transfer> findTransactions() {
        return transferRepository.findTransfersByFrom(sessionService.sessionUser());
    }


    public Double applyFee(Double amount) {
        return amount * 0.995;
    }


}
