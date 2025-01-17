package jr.dev.FlashCash.service;

import jr.dev.FlashCash.model.Transfer;
import jr.dev.FlashCash.model.User;
import jr.dev.FlashCash.interfaces.repository.TransferRepository;
import jr.dev.FlashCash.interfaces.repository.UserAccountRepository;
import jr.dev.FlashCash.interfaces.repository.UserRepository;
import jr.dev.FlashCash.service.form.AddToFlashCashForm;
import jr.dev.FlashCash.service.form.CashToBankForm;
import jr.dev.FlashCash.service.form.TransferForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final UserAccountRepository userAccountRepository;
    private final SessionService sessionService;
    private final TransferRepository transferRepository;
    private final UserRepository userRepository;

    public void transferCashToAccount(AddToFlashCashForm form) {
        if(form != null){
            userAccountRepository.save(sessionService.sessionUser().getAccount().plus(form.getAmount()));
        }
    }

    public String transfer(TransferForm form) { // TO COMPLETE WITH ERROR MESSAGE ON CONSTRAINT

        if(form!= null){
            User to = userRepository.findUserByEmail(form.getContactEmail())
                    .orElseThrow(()-> new RuntimeException("User with email not found"));

        // fill transfer with information
        Transfer currentTransfer = new Transfer();
        currentTransfer.setFrom(sessionService.sessionUser()); // Gets whole user
        currentTransfer.setTo(to); // Gets whole user
        currentTransfer.setDateTime(LocalDateTime.now());
        currentTransfer.setDescription(form.getDescription());
        currentTransfer.setAmountBeforeFee(form.getAmount());
        currentTransfer.setAmountAfterFee(applyFee(form.getAmount()));

            if(sessionService.sessionUser().getAccount().getAmount() - form.getAmount() > 0){
                // Update Sender & Receiver accounts amounts and save in repo
                userAccountRepository.save(sessionService.sessionUser().getAccount().minus(currentTransfer.getAmountAfterFee())); // Get the transfer amount >> form amount
                userAccountRepository.save(to.getAccount().plus(currentTransfer.getAmountAfterFee()));
                transferRepository.save(currentTransfer);
                // Retour succès
                return "Transfer successful!";
            }
            else {
                // Retour échec si le solde est insuffisant
                return "You need more money";
            }
        }
        return "Invalid transfer form";
    }

    public List<Transfer> findTransactions() {
//        return transferRepository.findTransfersByFrom(sessionService.sessionUser());
        return transferRepository.returnTransfers(sessionService.sessionUser().getId());
    }

    public Double applyFee(Double amount) {
        return amount * 0.995;
    }

    public void transferCashToBank(CashToBankForm form) {
        if(sessionService.sessionUser().getAccount().getIban().equals(form.getIban())){
            userAccountRepository.save(sessionService.sessionUser().getAccount().minus(form.getAmount()));
        }
    }

    public String findIban() {
         try {
             return sessionService.sessionUser().getAccount().getIban();
         }
            catch (NullPointerException ex){
                return "No IBAN found";
            }
    }
}
