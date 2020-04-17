package by.bestlunch.util;

import by.bestlunch.persistence.model.Restaurant;
import by.bestlunch.persistence.model.Vote;
import by.bestlunch.web.dto.RestaurantDto;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RestaurantUtil {

    private RestaurantUtil() {
    }

    public static List<RestaurantDto> getDtoWithCount(int userId, Collection<Restaurant> restaurants,
                                                      Collection<Vote> votes) {
        return getAllWithCount(userId, restaurants, votes, restaurant -> true);
    }

    public static List<RestaurantDto> getFilteredWithCount(int userId, Collection<Restaurant> restaurants,
                                                           Collection<Vote> votes,
                                                           @Nullable LocalDate startDate,
                                                           @Nullable LocalDate endDate) {
        return getAllWithCount(userId, restaurants, votes, vote ->
                Util.isBetweenInclusive(vote.getVotingDate(), startDate, endDate));

    }

    private static List<RestaurantDto> getAllWithCount(int userId, Collection<Restaurant> restaurants,
                                                       Collection<Vote> votes, Predicate<Vote> filter) {

        Map<Restaurant, Long> map = votes.stream().filter(filter)
                .collect(Collectors.groupingBy(Vote::getRestaurant, Collectors.counting()));

        return restaurants.stream()
                .map(restaurant -> getRestaurantDto(restaurant, map.get(restaurant),
                        getVoteForAuthUserPerDate(votes, userId, restaurant, LocalDate.now())))
                .collect(Collectors.toList());
    }

    public static RestaurantDto getRestaurantDto(Restaurant restaurant, Long amount, Boolean vote) {
        return new RestaurantDto(restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getDescription(),
                restaurant.getAdded(),
                Math.toIntExact(Objects.requireNonNullElse(amount, 0L)),
                vote);
    }

    private static boolean getVoteForAuthUserPerDate(Collection<Vote> votes, int userId, Restaurant restaurant, LocalDate localDate) {
        return votes.stream().anyMatch(vote -> vote.getVotingDate()
                .isEqual(localDate) && vote.getUser().getId() == userId && vote.getRestaurant().getId().equals(restaurant.id()));
    }
}