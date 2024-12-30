package jr.dev.FlashCash.service.form;

import lombok.Data;

@Data
public class TransferForm {

    private String contactEmail;
    private Double amount;

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
