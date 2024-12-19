package jr.dev.FlashCash.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity

public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;

    private Double balance;

//    @Column (unique = true, nullable = false)
    private String iban;


    public UserAccount plus(double amount) {
        this.balance += amount;
        return this;
    }

    public UserAccount minus(double amount){
        this.balance -= amount;
        return this;
    }

}
