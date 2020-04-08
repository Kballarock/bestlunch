package by.bestlunch.web.controller.json;

import by.bestlunch.persistence.model.Restaurant;
import by.bestlunch.persistence.model.User;
import by.bestlunch.web.json.JsonUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static by.bestlunch.RestaurantTestData.*;
import static by.bestlunch.UserTestData.USER;

class JsonUtilTest {

    @Test
    void readWriteValue() {
        String json = JsonUtil.writeValue(RESTAURANT_BURGER_KING);
        System.out.println(json);
        Restaurant restaurant = JsonUtil.readValue(json, Restaurant.class);
        RESTAURANT_MATCHERS.assertMatch(restaurant, RESTAURANT_BURGER_KING);
    }

    @Test
    void readWriteValues() {
        String json = JsonUtil.writeValue(RESTAURANTS);
        System.out.println(json);
        List<Restaurant> restaurant = JsonUtil.readValues(json, Restaurant.class);
        RESTAURANT_MATCHERS.assertMatch(restaurant, RESTAURANTS);
    }

    @Test
    void writeOnlyAccess() {
        String json = JsonUtil.writeValue(USER);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = JsonUtil.writeAdditionProps(USER, "password", "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
}