package by.bestlunch.util;

import by.bestlunch.persistence.model.Role;
import by.bestlunch.persistence.model.User;
import by.bestlunch.web.dto.UserDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class UserUtil {

    public static User createNewFromDto(UserDto userDto) {
        return new User(null, userDto.getName(), userDto.getEmail(), userDto.getPassword(), Role.ROLE_USER);
    }

    public static UserDto asDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }

    public static User updateFromDto(User user, UserDto userDto) {
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail().toLowerCase());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public static User prepareToSave(User user, BCryptPasswordEncoder passwordEncoder) {
        String password = user.getPassword();
        user.setPassword(StringUtils.hasText(password) ? passwordEncoder.encode(password) : password);
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

    public static URI createNewURI(String url, int id) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(url + "/{id}")
                .buildAndExpand(id).toUri();
    }
}