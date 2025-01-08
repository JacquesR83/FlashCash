package jr.dev.FlashCash.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jr.dev.FlashCash.interfaces.validatorConstraints.StrongPassword;
import lombok.*;

import java.util.List;

@Data
@Entity
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email; //login

    private String firstname;
    private String lastname;

//    @NotNull(message= "Password needed")

//    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/]).{8,}$",
//            message = "The password must contain 8 characters, including an uppercase letter, a number, and a symbol")
    @NotBlank(message = "Password is mandatory")
    @StrongPassword // Useless as it was already checked before in the SignUpForm, is checked at saving in the repo
    private String password;

    @ManyToMany
    private List<Link> links;

    @OneToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserAccount account;

}
