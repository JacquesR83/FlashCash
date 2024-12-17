package jr.dev.FlashCash.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity

public class UserAccount {

    @Id
    @GeneratedValue
    private Integer accountId;

    private Double balance;

    @Column (unique = true, nullable = false)
    private String iban;
//    @Column (unique = true, nullable = false)
//    private String accountNumber;

    public UserAccount plus(double amount) {
        this.balance += amount;
        return this;
    }

    public UserAccount minus(double amount){
        this.balance -= amount;
        return this;
    }




}
