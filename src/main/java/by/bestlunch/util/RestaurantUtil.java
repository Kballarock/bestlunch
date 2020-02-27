package by.bestlunch.util;

import by.bestlunch.persistence.model.Restaurant;
import by.bestlunch.persistence.model.Role;
import by.bestlunch.persistence.model.User;
import by.bestlunch.persistence.model.Vote;
import by.bestlunch.web.dto.RestaurantDto;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.of;
import static java.time.LocalDate.of;


public class RestaurantUtil {

    public static final User ADMIN = new User(100000, "Admin", "admin@admin.com", "user", true, of(2019, 12, 16), Collections.singleton(Role.ROLE_ADMIN));
    public static final User USER = new User(100001, "User", "user@user.com", "admin", true, of(2019, 12, 16), Collections.singleton(Role.ROLE_USER));


    public static void main(String[] args) {
        //RESTAURANTS
        Restaurant burgerKing = new Restaurant(100000, "Burger King", "Быстрое питание", "г. Минск. ул. Кирова, 10", of(2019, Month.DECEMBER, 16, 1, 0));
        Restaurant renaissance = new Restaurant(100001, "Renaissance", "Итальянская кухня", "г. Минск, ул. Сурганова, 5", of(2019, Month.DECEMBER, 16, 2, 0));
        List<Restaurant> restaurants = List.of(burgerKing, renaissance);

        //VOTES
        Vote vote1 = new Vote(1000, ADMIN, burgerKing, of(2019, Month.DECEMBER, 16));
        Vote vote2 = new Vote(1001, USER, renaissance, of(2019, Month.DECEMBER, 16));
        Vote vote3 = new Vote(1002, ADMIN, burgerKing, of(2019, Month.DECEMBER, 17));
        Vote vote4 = new Vote(1003, USER, burgerKing, of(2019, Month.DECEMBER, 17));
        Vote vote5 = new Vote(1004, ADMIN, renaissance, of(2019, Month.DECEMBER, 18));
        Vote vote6 = new Vote(1005, USER, renaissance, of(2019, Month.DECEMBER, 18));
        Vote vote7 = new Vote(1006, ADMIN, renaissance, of(2020, Month.FEBRUARY, 21));
        Vote vote8 = new Vote(1007, USER, renaissance, of(2020, Month.FEBRUARY, 21));
        List<Vote> votes = List.of(vote1, vote2, vote3, vote4, vote5, vote6, vote7, vote8);

        System.out.println(LocalDate.now());

        //Получаем все голоса за указанную дату
        System.out.println("Получаем все голоса за указанную дату");
        List<Vote> votesWithDateParam = getAllByDateVoting(votes, of(2019, Month.DECEMBER, 16));
        votesWithDateParam.forEach(System.out::println);
        System.out.println();


        //Получаем список ресторанов со счетчиком голосов
        System.out.println("Получаем список ресторанов со счетчиком голосов");
        List<RestaurantDto> dtoList = getAllWithCount(restaurants, votesWithDateParam, restaurant -> true);
        dtoList.forEach(System.out::println);
        System.out.println();


     /*   List<Vote> list = getUserVoteForRestaurantPerDate(ADMIN, votes, of(2019, Month.DECEMBER, 18));
        list.forEach(System.out::println);*/

        //Фильтр за период
        System.out.println("Фильтр за период");
        List<RestaurantDto> filter = getFilteredWithCount(restaurants, votes, of(2019, Month.DECEMBER, 15), of(2020, Month.FEBRUARY, 21));
        filter.forEach(System.out::println);

    }

    //----------------------------------------------------------------------------------------------------------------
    public static List<Vote> getAllByDateVoting(Collection<Vote> votes, LocalDate dateVoting) {
        return votes.stream().filter(date -> date.getVotingDate().isEqual(dateVoting)).collect(Collectors.toList());
    }

    public static List<RestaurantDto> getFilteredWithCount(Collection<Restaurant> restaurants,
                                                           Collection<Vote> votes,
                                                           @Nullable LocalDate startDate,
                                                           @Nullable LocalDate endDate) {
        return getAllWithCount(restaurants, votes, vote ->
                Util.isBetweenInclusive(vote.getVotingDate(), startDate, endDate));

    }


    public static List<RestaurantDto> getAllWithCount(Collection<Restaurant> restaurants,
                                                      Collection<Vote> votes, Predicate<Vote> filter) {

        Map<Restaurant, Long> map = votes.stream().filter(filter)
                .collect(Collectors.groupingBy(Vote::getRestaurant, Collectors.counting()));

        return restaurants.stream()
                .map(restaurant -> getRestaurantDto(restaurant, map.get(restaurant))).collect(Collectors.toList());
    }

    private static RestaurantDto getRestaurantDto(Restaurant restaurant, Long amount) {
        return new RestaurantDto(restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getDescription(),
                restaurant.getAdded(),
                Math.toIntExact(Objects.requireNonNullElse(amount, 0L)));
    }
    //-----------------------------------------------------------------------------------------------------------------


    public static List<Vote> getUserVoteForRestaurantPerDate(User user, List<Vote> votes, LocalDate dateVoting) {
        return votes.stream().filter(date -> date.getVotingDate().isEqual(dateVoting)).filter(vote -> vote.getUser().getId().equals(user.getId())).collect(Collectors.toList());
    }

  /*  public static Restaurant createNewRestaurantFromDto(RestaurantDto restaurantDto) {
        return new Restaurant(null, restaurantDto.getName(), restaurantDto.getDescription(), restaurantDto.getAddress());
    }*/

    public static Restaurant updateRestaurantFromDto(Restaurant restaurant, RestaurantDto restaurantDto) {
        restaurant.setName(restaurantDto.getName());
        restaurant.setDescription(restaurantDto.getDescription());
        restaurant.setAddress(restaurantDto.getAddress());
        return restaurant;
    }
}