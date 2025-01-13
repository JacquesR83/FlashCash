package jr.dev.FlashCash.service.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CashToBankForm {
    @Positive
    private Double amount;
    @NotEmpty
    private String iban;
}
