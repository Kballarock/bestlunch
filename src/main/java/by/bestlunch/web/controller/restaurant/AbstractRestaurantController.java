package by.bestlunch.web.controller.restaurant;

import by.bestlunch.persistence.model.Restaurant;
import by.bestlunch.persistence.model.Vote;
import by.bestlunch.service.RestaurantService;
import by.bestlunch.service.VoteService;
import by.bestlunch.util.RestaurantUtil;
import by.bestlunch.util.SecurityUtil;
import by.bestlunch.web.dto.RestaurantDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

import static by.bestlunch.util.RestaurantUtil.getAllWithCount;
import static by.bestlunch.validation.ValidationUtil.assureIdConsistent;
import static by.bestlunch.validation.ValidationUtil.checkNew;

public abstract class AbstractRestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final LocalDate LOCAL_DATE = LocalDate.now();

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private VoteService voteService;

    public List<Restaurant> getAll() {
        log.info("getAll");
        return restaurantService.getAll();
    }

    public List<RestaurantDto> getAllWithCountPerDate() {
        log.info("get restaurants with count votes on date = {}", LOCAL_DATE);
        List<Vote> votes = voteService.getAllByDate(LOCAL_DATE);
        return getAllWithCount(getAll(), votes, restaurant -> true);
    }


    public List<RestaurantDto> getBetween(@Nullable LocalDate startDate,
                                          @Nullable LocalDate endDate) {
        log.info("getBetween dates({} - {})", startDate, endDate);
        List<Vote> votesDateFiltered = voteService.getBetween(startDate, endDate);
        return RestaurantUtil.getFilteredWithCount(getAll(), votesDateFiltered, startDate, endDate);
    }

    public Restaurant get(int id) {
        log.info("get {}", id);
        return restaurantService.get(id);
    }

    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        return restaurantService.create(restaurant);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        restaurantService.delete(id);
    }

    public void update(Restaurant restaurant, int id) {
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        restaurantService.update(restaurant);
    }

    public void createOrUpdateVote(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("update vote for restaurant id {}", id);
        voteService.update(userId, id);
    }

    public void deleteVote(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete vote for restaurant id {}", id);
        voteService.delete(id, userId);
    }
}