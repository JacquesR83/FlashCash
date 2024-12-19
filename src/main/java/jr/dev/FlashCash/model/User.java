package jr.dev.FlashCash.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@Entity
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @Column(unique = true, nullable = false)
//    @Email(message = "Email should be valid")
    private String email; //login

    private String firstname;
    private String lastname;

//    @NotNull(message= "Password needed")
    private String password;

    @ManyToMany
    private List<Link> links;

    @OneToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserAccount account;

}
