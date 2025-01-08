package jr.dev.FlashCash.model.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jr.dev.FlashCash.interfaces.validatorConstraints.PasswordMatch;
import jr.dev.FlashCash.model.dto.SignUpForm;

public class PasswordMatchValidator implements ConstraintValidator <PasswordMatch, SignUpForm> {

    @Override
    public boolean isValid(SignUpForm form, ConstraintValidatorContext constraintValidatorContext) {
        if (form== null){
            return true;
        }

        // Needs further confirmation as 2 fields are involved
        boolean valid = form.getPassword().equals(form.getConfirmPassword());

        if (!valid) {
            // if passwords don't match, disable original constraint violation settings
            constraintValidatorContext.disableDefaultConstraintViolation();
            // Build a constraint
            constraintValidatorContext.buildConstraintViolationWithTemplate("Password fields don't match")
                    //set to the th selected field to compare
                    .addPropertyNode("confirmPassword")
                    // add a new constraint violation to the constraintValidatorContext
                    .addConstraintViolation();
        }

        return valid;
    }
}
