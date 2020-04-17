package by.bestlunch.web.controller;

import org.junit.jupiter.api.Test;

import static by.bestlunch.UserTestData.USER;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RootControllerTest extends AbstractControllerTest {

    RootControllerTest() {
        super("");
    }

    @Test
    void getWelcome() throws Exception {
        perform(doGet("welcome").auth(USER))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/welcome.jsp"));
    }
}