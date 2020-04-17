package by.bestlunch.web.controller.restaurant;

import by.bestlunch.RestaurantTestData;
import by.bestlunch.persistence.model.Restaurant;
import by.bestlunch.persistence.model.Vote;
import by.bestlunch.service.RestaurantService;
import by.bestlunch.service.VoteService;
import by.bestlunch.util.exception.NotFoundException;
import by.bestlunch.web.controller.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;

import static by.bestlunch.RestaurantTestData.*;
import static by.bestlunch.TestUtil.readFromJson;
import static by.bestlunch.TestUtil.readFromJsonMvcResult;
import static by.bestlunch.UserTestData.ADMIN;
import static by.bestlunch.util.RestaurantUtil.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantAdminRestControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private VoteService voteService;

    RestaurantAdminRestControllerTest() {
        super(RestaurantAdminRestController.RESTAURANT_ADMIN_REST_URL);
    }

    @Test
    void get() throws Exception {
        perform(doGet(RESTAURANT_BURGER_KING.id()).basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHERS.contentJson(RESTAURANT_BURGER_KING))
                .andExpect(result -> RESTAURANT_MATCHERS.assertMatch(readFromJsonMvcResult(result, Restaurant.class), RESTAURANT_BURGER_KING));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(doGet(RESTAURANT_BURGER_KING.id()))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void getNotFound() throws Exception {
        perform(doGet(12).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        perform(doDelete(RESTAURANT_RENAISSANCE.id()).basicAuth(ADMIN))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> restaurantService.get(RESTAURANT_RENAISSANCE.id()));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(doDelete(125).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdated();
        perform(doPut(RESTAURANT_RENAISSANCE.id()).jsonBody(updated).basicAuth(ADMIN))
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHERS.assertMatch(restaurantService.get(RESTAURANT_RENAISSANCE.id()), updated);
    }

    @Test
    void createNewRestaurant() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        ResultActions action = perform(doPost().jsonBody(newRestaurant).basicAuth(ADMIN));

        Restaurant created = readFromJson(action, Restaurant.class);
        Integer newId = created.getId();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHERS.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHERS.assertMatch(restaurantService.get(newId), newRestaurant);
    }

    @Test
    void getAllWithCountPerDate() throws Exception {
        List<Vote> votes = voteService.getAllByDate(LocalDate.now());
        perform(doGet().basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_DTO_MATCHERS.contentJson(getDtoWithCount(ADMIN.getId(), RESTAURANTS, votes)));
    }

    @Test
    void getBetween() throws Exception {
        perform(doGet("filter").basicAuth(ADMIN).unwrap()
                .param("startDate", "2019-12-16")
                .param("endDate", "2019-12-16"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(RESTAURANT_DTO_MATCHERS.contentJson(
                        getRestaurantDto(RESTAURANT_BURGER_KING, 1L, false),
                        getRestaurantDto(RESTAURANT_RENAISSANCE, 1L, false)));
    }

    @Test
    void getBetweenForAllDates() throws Exception {
        List<Vote> votes = voteService.getBetween(null, null);
        perform(doGet("filter?startDate=&endTime=").basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andExpect(RESTAURANT_DTO_MATCHERS
                        .contentJson(getDtoWithCount(ADMIN.getId(), RESTAURANTS, votes)));
    }
}