package jr.dev.FlashCash.model;

import jakarta.persistence.*;
import lombok.*;

import java.lang.String;
import java.time.LocalDateTime;

@Data
@Entity

public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDateTime dateTime;
    private Double amountBeforeFee;
    private Double amountAfterFee;

//    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    @ManyToOne
    @JoinColumn(name = "account_sender_id")
    private User from;
    @ManyToOne
    @JoinColumn(name = "account_receiver_id")
    private User to;

//
//    @Enumerated(EnumType.STRING)
//    private String currency;
//
//    @Enumerated(EnumType.STRING)
//    private String transactionType;
//
//    @Enumerated(EnumType.STRING)
//    private Status status;

}
