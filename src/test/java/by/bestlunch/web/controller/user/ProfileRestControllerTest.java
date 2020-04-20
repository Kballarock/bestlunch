package by.bestlunch.web.controller.user;

import by.bestlunch.persistence.model.User;
import by.bestlunch.service.UserService;
import by.bestlunch.util.UserUtil;
import by.bestlunch.web.controller.AbstractControllerTest;
import by.bestlunch.web.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static by.bestlunch.TestUtil.readFromJson;
import static by.bestlunch.UserTestData.*;
import static by.bestlunch.util.exception.ErrorType.VALIDATION_ERROR;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    ProfileRestControllerTest() {
        super(ProfileRestController.REST_URL);
    }

    @Test
    void get() throws Exception {
        perform(doGet().basicAuth(USER))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHERS.contentJson(USER));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(doGet())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        perform(doDelete().basicAuth(USER))
                .andExpect(status().isNoContent());
        USER_MATCHERS.assertMatch(userService.getAll(), ADMIN);
    }

    @Test
    void register() throws Exception {
        UserDto newDto = new UserDto(null, "newUsername", "newemail@gmail.com", "newPassword");
        User newUser = UserUtil.createNewFromDto(newDto);
        ResultActions action = perform(doPost("/register").jsonBody(newDto))
                .andDo(print())
                .andExpect(status().isCreated());

        User created = readFromJson(action, User.class);
        Integer newId = created.getId();
        newUser.setId(newId);
        USER_MATCHERS.assertMatch(created, newUser);
        USER_MATCHERS.assertMatch(userService.get(newId), newUser);
    }

    @Test
    void update() throws Exception {
        UserDto updateDto = new UserDto(null, "newName", "newemail@yandex.ru", "newPassword", "newPassword");
        perform(doPut().jsonBody(updateDto).basicAuth(USER))
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_MATCHERS.assertMatch(userService.get(USER.id()), UserUtil.updateFromDto(new User(USER), updateDto));
    }

    @Test
    void updateInvalid() throws Exception {
        UserDto updatedDto = new UserDto(null, null, "admin@admin", null);

        perform(doPut().jsonBody(updatedDto).basicAuth(USER))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        UserDto updatedDto = new UserDto(null, "newName", "admin@admin.com", "newPassword");

        perform(doPut().jsonBody(updatedDto).basicAuth(USER))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                //.andExpect(detailMessage(EXCEPTION_DUPLICATE_EMAIL))
                .andDo(print());
    }
}