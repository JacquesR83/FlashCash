package jr.dev.FlashCash.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Entity

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    @Email(message = "Email should be valid")
    private String email; //login

    private String firstname;
    private String lastname;

    @NotNull(message= "Password needed")
    private String password;

    @ManyToMany
    private List<Link> links;

    @OneToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    }
