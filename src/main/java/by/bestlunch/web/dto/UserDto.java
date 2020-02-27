package by.bestlunch.web.dto;

import by.bestlunch.validation.view.View;
import by.bestlunch.util.HasEmail;
import by.bestlunch.validation.view.ErrorSequence;
import by.bestlunch.validation.PasswordMatches;
import by.bestlunch.validation.ValidEmail;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.Transient;
import javax.validation.constraints.*;
import java.io.Serializable;

@PasswordMatches(first = "password", second = "matchingPassword", groups = ErrorSequence.Third.class)
public class UserDto extends BaseDto implements HasEmail, Serializable {
    private static final long serialVersionUID = 1L;

    @SafeHtml(groups = {View.Web.class}, message = "{userDto.SafeHtml}")
    @NotBlank(message = "{userDto.NotBlank.name}", groups = ErrorSequence.First.class)
    @Size(min = 3, max = 100, message = "{userDto.Size.name}", groups = ErrorSequence.Second.class)
    private String name;

    @SafeHtml(groups = {View.Web.class}, message = "{userDto.SafeHtml}")
    @NotBlank(message = "{userDto.NotBlank.email}", groups = ErrorSequence.First.class)
    @ValidEmail(groups = ErrorSequence.Second.class)
    private String email;

    @NotBlank(message = "{userDto.NotBlank.password}", groups = ErrorSequence.First.class)
    @Size(min = 6, message = "{userDto.Size.password}", groups = ErrorSequence.Second.class)
    private String password;

    @Transient
    @NotBlank(message = "{userDto.NotBlank.password}", groups = ErrorSequence.First.class)
    @Size(min = 6, message = "{userDto.Size.password}", groups = ErrorSequence.Second.class)
    private String matchingPassword;

    public UserDto() {
    }

    public UserDto(Integer id, String name, String email, String password) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}