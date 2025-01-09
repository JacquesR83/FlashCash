package jr.dev.FlashCash.model.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jr.dev.FlashCash.interfaces.validatorConstraints.IbanFormat;

import java.math.BigInteger;
import java.util.regex.Pattern;

public class IbanFormatValidator implements ConstraintValidator <IbanFormat, String> {

    @Override
    public void initialize(IbanFormat constraintAnnotation) {
    }

    private static final String IBAN_PATTERN = "^[A-Z]{2}\\d{2}[A-Z0-9]{8,30}$";

    @Override
    public boolean isValid(String iban, ConstraintValidatorContext constraintValidatorContext) {

        if (iban == null || iban.length() < 15 || iban.length() > 30) {
            return false;
        }

        //Stock iban as pattern
        Pattern pattern = Pattern.compile(IBAN_PATTERN);

        // Slice IBAN pattern in 3 parts
        String countryCode = iban.substring(0, 2);
        String checkDigits = iban.substring(2, 4);
        String bban = iban.substring(4);

        if (!pattern.matcher(iban).matches()) {
            return false;
        }

        // Check countryCode & checkDigits are valid
        if (!isValidCountryCode(countryCode) || !isValidCheckDigits(checkDigits)) {
            return false;
        }

        // check fields


        if (!bban.matches("[A-Z0-9]+")) {
            return false;
        }


        return mod97Check(iban);
    }


    private static boolean mod97Check(String iban) {
        // Country location (2 letters + 2 digits) are added at the end of the string to perform mod97 check
        String rearrangedIban = iban.substring(4) + iban.substring(0, 4);

        //Convert iban to numbers only
        StringBuilder numericIban = new StringBuilder();
        for (char c : rearrangedIban.toCharArray()) {
            // convert letters to numbers (A=10...Z=35)
            if (Character.isLetter(c)) {
                numericIban.append(c - 'A' + 10);
                // append digits as is
            } else {
                numericIban.append(c);
            }
        }
        return mod97(numericIban.toString());
    }

    // Verification for mod97 check
    private static boolean mod97(String numericIban){
        BigInteger ibanBigInt = new BigInteger(numericIban);
        return ibanBigInt.mod(BigInteger.valueOf(97)).equals(BigInteger.ONE);
    }


    private static boolean isValidCountryCode(String countryCode){
        return countryCode.matches("^[A-Z]{2}$");
    }

    private static boolean isValidCheckDigits (String checkDigits){
        return checkDigits.matches("^[0-9]{2}$");
    }


}