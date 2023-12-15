package qrcodeapi.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEmptyValidator implements ConstraintValidator<NotEmpty, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value!=null && !value.equals("");
    }
}
