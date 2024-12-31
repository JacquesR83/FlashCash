package jr.dev.FlashCash.model;

import jakarta.persistence.*;
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

//    @NotNull(message= "Password needed")
    private java.lang.String password;

    @ManyToMany
    private List<Link> links;

    @OneToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserAccount account;

}
