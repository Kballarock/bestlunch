package by.bestlunch.web.controller.menu;

import by.bestlunch.service.MenuService;
import by.bestlunch.web.controller.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static by.bestlunch.MenuTestData.MENU_MATCHERS;
import static by.bestlunch.RestaurantTestData.RESTAURANT_BURGER_KING;
import static by.bestlunch.UserTestData.USER;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuUserRestControllerTest extends AbstractControllerTest {

    @Autowired
    private MenuService menuService;

    MenuUserRestControllerTest() {
        super("/rest/restaurant/" + RESTAURANT_BURGER_KING.id() + "/menu");
    }

    @Test
    void getAllByDate() throws Exception {
        perform(doGet().basicAuth(USER).unwrap()
                .param("date", "2019-12-17"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHERS.contentJson(menuService.getAllByDate(RESTAURANT_BURGER_KING.id(), LocalDate.of(2019, 12, 17))));
    }
}