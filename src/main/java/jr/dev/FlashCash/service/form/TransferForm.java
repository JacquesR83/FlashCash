package jr.dev.FlashCash.service.form;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class TransferForm {

    private String contactEmail;
    private Double amount;

    private String description;

}
