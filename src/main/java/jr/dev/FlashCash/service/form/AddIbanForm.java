package jr.dev.FlashCash.service.form;

import jr.dev.FlashCash.interfaces.validatorConstraints.IbanFormat;
import lombok.Data;

@Data
public class AddIbanForm {
    @IbanFormat
    private String iban;
}
