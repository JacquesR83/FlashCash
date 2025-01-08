package jr.dev.FlashCash.interfaces.validatorConstraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jr.dev.FlashCash.model.validator.PasswordMatchValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
public @interface PasswordMatch {
    String message() default("Passwords don't match");
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};

}
