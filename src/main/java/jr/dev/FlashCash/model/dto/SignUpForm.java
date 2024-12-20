package jr.dev.FlashCash.model.dto;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class SignUpForm {

    String firstname;
    String lastname;
    String email;
    String account;
    String password;
    String confirmPassword;

}
