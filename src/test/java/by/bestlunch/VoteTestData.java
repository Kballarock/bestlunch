package by.bestlunch;

import by.bestlunch.persistence.model.Vote;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static by.bestlunch.RestaurantTestData.RESTAURANT_BURGER_KING;
import static by.bestlunch.RestaurantTestData.RESTAURANT_RENAISSANCE;
import static by.bestlunch.UserTestData.ADMIN;
import static by.bestlunch.UserTestData.USER;
import static java.time.LocalDate.of;

public class VoteTestData {

    public static final Vote VOTE1 = new Vote(1000, ADMIN, RESTAURANT_BURGER_KING, of(2019, Month.DECEMBER, 16));
    public static final Vote VOTE2 = new Vote(1001, USER, RESTAURANT_RENAISSANCE, of(2019, Month.DECEMBER, 16));
    public static final Vote VOTE3 = new Vote(1002, ADMIN, RESTAURANT_BURGER_KING, of(2019, Month.DECEMBER, 17));
    public static final Vote VOTE4 = new Vote(1003, USER, RESTAURANT_BURGER_KING, of(2019, Month.DECEMBER, 17));
    public static final Vote VOTE5 = new Vote(1004, ADMIN, RESTAURANT_RENAISSANCE, of(2019, Month.DECEMBER, 18));
    public static final Vote VOTE6 = new Vote(1005, USER, RESTAURANT_RENAISSANCE, of(2019, Month.DECEMBER, 18));
    public static final Vote VOTE7 = new Vote(1006, ADMIN, RESTAURANT_RENAISSANCE, LocalDate.now());

    public static final List<Vote> VOTES = List.of(VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, VOTE6, VOTE7);

    public static Vote getNew() {
        return new Vote(null, USER, RESTAURANT_BURGER_KING,  LocalDate.now());
    }

    public static Vote getUpdated() {
        return new Vote(VOTE7.id(), ADMIN, RESTAURANT_BURGER_KING, LocalDate.now());
    }

   /* public static TestMatchers<Meal> MEAL_MATCHERS = TestMatchers.useFieldsComparator(Meal.class, "user");
    public static TestMatchers<MealTo> MEAL_TO_MATCHERS = TestMatchers.useEquals(MealTo.class);*/
}