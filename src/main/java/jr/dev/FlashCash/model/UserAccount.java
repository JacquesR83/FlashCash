package jr.dev.FlashCash.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity

public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;

    private Double amount;

//    @Column (unique = true, nullable = false)
    private String iban;


    public UserAccount plus(double amount) {
        this.amount += amount;
        return this;
    }

    public UserAccount minus(double amount){
        this.amount -= amount;
        return this;
    }

}
