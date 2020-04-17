package by.bestlunch;

import by.bestlunch.persistence.model.Restaurant;
import by.bestlunch.web.dto.RestaurantDto;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;

public class RestaurantTestData {

    public static final Restaurant RESTAURANT_BURGER_KING = new Restaurant(100000, "Burger King", "Быстрое питание", "г. Минск. ул. Кирова, 10", of(2019, Month.DECEMBER, 16, 1, 0));
    public static final Restaurant RESTAURANT_RENAISSANCE = new Restaurant(100001, "Renaissance", "Итальянская кухня", "г. Минск, ул. Сурганова, 5", of(2019, Month.DECEMBER, 16, 2, 0));

    public static final List<Restaurant> RESTAURANTS = List.of(RESTAURANT_BURGER_KING, RESTAURANT_RENAISSANCE);

    public static Restaurant getNew() {
        return new Restaurant(null, "NewRestaurant", "NewKitchen", "NewAddress", LocalDateTime.now());
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(RESTAURANT_RENAISSANCE);
        updated.setName("UpdatedRestaurantName");
        updated.setDescription("UpdateNewKitchen");
        return updated;
    }

    public static TestMatchers<Restaurant> RESTAURANT_MATCHERS = TestMatchers.useFieldsComparator(Restaurant.class, "added");
    public static TestMatchers<RestaurantDto> RESTAURANT_DTO_MATCHERS = TestMatchers.useEquals(RestaurantDto.class);
}