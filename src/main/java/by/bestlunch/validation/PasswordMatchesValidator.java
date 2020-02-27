package by.bestlunch.validation;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    private String firstPassword;
    private String secondPassword;
    private String message;

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        firstPassword = constraintAnnotation.first();
        secondPassword = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            final Object firstObj = BeanUtils.getProperty(value, firstPassword);
            final Object secondObj = BeanUtils.getProperty(value, secondPassword);

            valid = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        } catch (final Exception ignore) {
        }

        if (!valid) {
            constraintPassword(context, firstPassword);
            constraintPassword(context, secondPassword);
        }
        return valid;
    }

    private void constraintPassword(ConstraintValidatorContext context, String password) {
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(password)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }
}