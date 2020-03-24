package by.bestlunch;

import by.bestlunch.persistence.model.Restaurant;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;

public class RestaurantTestData {

    public static final Restaurant RESTAURANT_BURGER_KING =new Restaurant(100000, "Burger King", "Быстрое питание", "г. Минск. ул. Кирова, 10", of(2019, Month.DECEMBER, 16, 1, 0));
    public static final Restaurant RESTAURANT_RENAISSANCE = new Restaurant(100001, "Renaissance", "Итальянская кухня", "г. Минск, ул. Сурганова, 5", of(2019, Month.DECEMBER, 16, 2, 0));

    public static final List<Restaurant> RESTAURANTS =List.of(RESTAURANT_BURGER_KING, RESTAURANT_RENAISSANCE);

    public static Restaurant getNew() {
        return new Restaurant(null, "NewRestaurant", "NewKitchen", "NewAddress", LocalDateTime.now());
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_BURGER_KING.id(), "Новый ресторан", "Новый ресторан","Новый ресторан", LocalDateTime.now());
    }

   /* public static TestMatchers<Meal> MEAL_MATCHERS = TestMatchers.useFieldsComparator(Meal.class, "user");
    public static TestMatchers<MealTo> MEAL_TO_MATCHERS = TestMatchers.useEquals(MealTo.class);*/
}