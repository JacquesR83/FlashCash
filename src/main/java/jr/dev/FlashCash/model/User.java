package jr.dev.FlashCash.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Data
@Entity
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private java.lang.String email; //login

    private java.lang.String firstname;
    private java.lang.String lastname;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/]).{8,}$",
            message = "Le mot de passe doit contenir 8 caractères dont une majuscule, un chiffre et un symbole")
    private java.lang.String password;

    @ManyToMany
    private List<Link> links;

    @OneToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserAccount account;

}
