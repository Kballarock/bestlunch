package by.bestlunch.web.controller.menu;

import by.bestlunch.MenuTestData;
import by.bestlunch.persistence.model.Menu;
import by.bestlunch.service.MenuService;
import by.bestlunch.util.exception.NotFoundException;
import by.bestlunch.web.controller.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static by.bestlunch.MenuTestData.*;
import static by.bestlunch.RestaurantTestData.RESTAURANT_BURGER_KING;
import static by.bestlunch.TestUtil.readFromJson;
import static by.bestlunch.TestUtil.readFromJsonMvcResult;
import static by.bestlunch.UserTestData.ADMIN;
import static by.bestlunch.util.exception.ErrorType.VALIDATION_ERROR;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuRestControllerTest extends AbstractControllerTest {

    @Autowired
    private MenuService menuService;

    MenuRestControllerTest() {
        super("/rest/restaurant/" + RESTAURANT_BURGER_KING.id() + "/menu/item");
    }

    @Test
    void get() throws Exception {
        perform(doGet(MENU_ITEM1.id()).basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> MENU_MATCHERS.assertMatch(readFromJsonMvcResult(result, Menu.class), MENU_ITEM1));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(doGet(MENU_ITEM1.id()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(doGet(15).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        perform(doDelete(MENU_ITEM1.id()).basicAuth(ADMIN))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> menuService.get(MENU_ITEM1.id(), RESTAURANT_BURGER_KING.id()));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(doDelete(15).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Menu updatedItem = getUpdated();
        perform(doPut(MENU_ITEM5.id()).jsonBody(updatedItem).basicAuth(ADMIN))
                .andExpect(status().isNoContent());

        MENU_MATCHERS.assertMatch(menuService.get(MENU_ITEM5.id(), RESTAURANT_BURGER_KING.id()), updatedItem);
    }

    @Test
    void updateInvalid() throws Exception {
        Menu invalid = new Menu(MENU_ITEM5.id(), "NewItem", 100000.00, LocalDate.now());
        perform(doPut(MENU_ITEM5.id()).jsonBody(invalid).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void createWithLocation() throws Exception {
        Menu newMenuItem = MenuTestData.getNew();
        ResultActions action = perform(doPost().jsonBody(newMenuItem).basicAuth(ADMIN));

        Menu createdItem = readFromJson(action, Menu.class);
        Integer newId = createdItem.getId();
        newMenuItem.setId(newId);
        MENU_MATCHERS.assertMatch(createdItem, newMenuItem);
        MENU_MATCHERS.assertMatch(menuService.get(newId, RESTAURANT_BURGER_KING.id()), newMenuItem);
    }

    @Test
    void createInvalid() throws Exception {
        Menu invalid = new Menu(null, null, 1.56, LocalDate.now());
        perform(doPost().jsonBody(invalid).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void getAllByDate() throws Exception {
        perform(doGet().basicAuth(ADMIN).unwrap()
                .param("date", "2019-12-17"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHERS.contentJson(menuService.getAllByDate(RESTAURANT_BURGER_KING.id(), LocalDate.of(2019, 12, 17))));
    }
}