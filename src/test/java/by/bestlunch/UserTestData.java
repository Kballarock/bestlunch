package by.bestlunch;

import by.bestlunch.persistence.model.Role;
import by.bestlunch.persistence.model.User;

import java.time.LocalDate;
import java.util.Collections;

import static java.time.LocalDate.of;

public class UserTestData {

    public static final User ADMIN = new User(100000, "Admin", "admin@admin.com", "user", true, of(2019, 12, 16), Collections.singleton(Role.ROLE_ADMIN));
    public static final User USER = new User(100001, "User", "user@user.com", "admin", true, of(2019, 12, 16), Collections.singleton(Role.ROLE_USER));

    public static User getNew() {
        return new User(null, "NewUser", "newuser@newuser.com", "password", true, LocalDate.now(), Collections.singleton(Role.ROLE_USER));
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("UpdatedUserName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        return updated;
    }

    public static TestMatchers<User> USER_MATCHERS = TestMatchers.useFieldsComparator(User.class, "registered", "password");
}