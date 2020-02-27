package by.bestlunch.validation;

import by.bestlunch.persistence.model.User;
import by.bestlunch.persistence.repository.UserRepository;
import by.bestlunch.util.HasEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import static by.bestlunch.web.exception.ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL;

@Component
public class UniqueMailValidator implements org.springframework.validation.Validator {

    private final UserRepository userRepository;

    @Autowired
    public UniqueMailValidator(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return HasEmail.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        HasEmail user = ((HasEmail) target);
        User dbUser = userRepository.getByEmail(user.getEmail().toLowerCase());
        if (dbUser != null && !dbUser.getId().equals(user.getId())) {
            errors.rejectValue("email", EXCEPTION_DUPLICATE_EMAIL);
        }
    }
}