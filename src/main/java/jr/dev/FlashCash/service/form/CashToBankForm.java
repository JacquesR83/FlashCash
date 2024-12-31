package jr.dev.FlashCash.service.form;

import lombok.Data;

@Data
public class CashToBankForm {
    private Double amount;
    private String iban;
}
