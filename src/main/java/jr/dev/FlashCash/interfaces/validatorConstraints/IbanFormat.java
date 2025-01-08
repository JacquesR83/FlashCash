package jr.dev.FlashCash.interfaces.validatorConstraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jr.dev.FlashCash.model.validator.IbanFormatValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IbanFormatValidator.class)
public @interface IbanFormat {
    String message() default("Provided Iban is invalid");
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
}
