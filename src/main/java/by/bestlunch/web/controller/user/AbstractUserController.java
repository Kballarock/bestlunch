package by.bestlunch.web.controller.user;

import by.bestlunch.persistence.model.User;
import by.bestlunch.service.UserService;
import by.bestlunch.util.UserUtil;
import by.bestlunch.validation.UniqueMailValidator;
import by.bestlunch.web.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.List;

import static by.bestlunch.validation.ValidationUtil.assureIdConsistent;
import static by.bestlunch.validation.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService service;

    @Autowired
    private UniqueMailValidator mailValidator;

    @InitBinder({"userDto", "user"})
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(mailValidator);
    }

    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public User get(int id) {
        log.info("getId {}", id);
        return service.get(id);
    }

    public User create(UserDto userDto) {
        log.info("create from to {}", userDto);
        return create(UserUtil.createNewFromDto(userDto));
    }

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return service.create(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
    }

    public void update(UserDto userDto, int id) {
        log.info("update {} with id={}", userDto, id);
        assureIdConsistent(userDto, id);
        service.update(userDto);
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return service.getByEmail(email);
    }

    public void enable(int id, boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        service.enable(id, enabled);
    }
}