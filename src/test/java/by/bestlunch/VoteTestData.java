package by.bestlunch;

import by.bestlunch.persistence.model.Vote;
import by.bestlunch.util.DateTimeUtil;

import java.time.Month;

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

    public static Vote getNewUser() {
        return new Vote(null, USER, RESTAURANT_BURGER_KING, DateTimeUtil.now());
    }

    public static Vote getUpdated() {
        Vote updated = new Vote(VOTE3);
        updated.setRestaurant(RESTAURANT_RENAISSANCE);
        return updated;
    }

    public static TestMatchers<Vote> VOTE_MATCHERS = TestMatchers.useFieldsComparator(Vote.class);
}