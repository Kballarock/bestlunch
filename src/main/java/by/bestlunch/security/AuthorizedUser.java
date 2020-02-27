package by.bestlunch.security;

import by.bestlunch.persistence.model.User;
import by.bestlunch.util.UserUtil;
import by.bestlunch.web.dto.UserDto;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    private UserDto userDto;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userDto = UserUtil.asDto(user);
    }

    public int getId() {
        return userDto.id();
    }

    public void update(UserDto newDto) {
        userDto = newDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    @Override
    public String toString() {
        return userDto.toString();
    }
}