package jr.dev.FlashCash.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jr.dev.FlashCash.interfaces.validatorConstraints.PasswordMatch;
import jr.dev.FlashCash.interfaces.validatorConstraints.StrongPassword;
import lombok.Data;

@Data
@PasswordMatch(message = "Passwords must match")
public class SignUpForm {
    @NotEmpty (message = "Please enter your firstname")
    String firstname;
    @NotEmpty (message = "Please enter your lastname")
    String lastname;
    @NotEmpty(message = "Email needed")
    String email;
    String account;
    @StrongPassword
    @NotBlank (message = "Password is mandatory")
    String password;
    @NotBlank (message = "Please confirm your password")
    String confirmPassword;

}
