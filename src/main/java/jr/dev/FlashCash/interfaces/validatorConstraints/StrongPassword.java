package jr.dev.FlashCash.interfaces.validatorConstraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jr.dev.FlashCash.model.validator.StrongPasswordValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StrongPasswordValidator.class)
public @interface StrongPassword {
    String message() default("The password must contain 8 characters, including an uppercase letter, a number, and a symbol");
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};

}
