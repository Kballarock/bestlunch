package by.bestlunch.service;

import by.bestlunch.persistence.model.Role;
import by.bestlunch.persistence.model.User;
import by.bestlunch.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.*;
import java.util.List;

import static by.bestlunch.UserTestData.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;

    @Test
    void create() {
        User newUser = getNew();
        User created = service.create(new User(newUser));
        Integer newId = created.getId();
        newUser.setId(newId);
        USER_MATCHERS.assertMatch(created, newUser);
        USER_MATCHERS.assertMatch(service.get(newId), newUser);
    }

    @Test
    void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", "user@user.com", "newPassword", Role.ROLE_USER)));
    }

    @Test
    void delete() {
        service.delete(USER.getId());
        assertThrows(NotFoundException.class, () ->
                service.delete(USER.getId()));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.delete(1));
    }

    @Test
    void get() {
        User user = service.get(ADMIN.getId());
        USER_MATCHERS.assertMatch(user, ADMIN);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.get(1));
    }

    @Test
    void getByEmail() {
        User user = service.getByEmail("admin@admin.com");
        USER_MATCHERS.assertMatch(user, ADMIN);
    }

    @Test
    void getAll() {
        List<User> all = service.getAll();
        USER_MATCHERS.assertMatch(all, ADMIN, USER);
    }

    @Test
    void update() {
        User updated = getUpdated();
        service.update(new User(updated));
        USER_MATCHERS.assertMatch(service.get(USER.getId()), updated);
    }

    @Test
    void enable() {
        service.enable(USER.getId(), false);
        assertFalse(service.get(USER.getId()).isEnabled());
        service.enable(USER.getId(), true);
        assertTrue(service.get(USER.getId()).isEnabled());
    }

    @Test
    void createWithException() {
        validateRootCause(() -> service.create(new User(null, "  ", "mail@mail.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "mail@mail.ru", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "mailmail.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "mail@", "password", Role.ROLE_USER)), ConstraintViolationException.class);
    }
}