package by.bestlunch.service;

import by.bestlunch.persistence.model.Restaurant;
import by.bestlunch.util.exception.NotFoundException;
import by.bestlunch.validation.view.ErrorSequence;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static by.bestlunch.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;
    private static Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void create() {
        Restaurant newRestaurant = getNew();
        Restaurant created = service.create(new Restaurant(newRestaurant));
        Integer newId = created.getId();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHERS.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHERS.assertMatch(service.get(newId), newRestaurant);
    }

    @Test
    void get() {
        Restaurant restaurant = service.get(RESTAURANT_BURGER_KING.getId());
        RESTAURANT_MATCHERS.assertMatch(restaurant, RESTAURANT_BURGER_KING);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.get(1));
    }

    @Test
    void delete() {
        service.delete(RESTAURANT_RENAISSANCE.getId());
        assertThrows(NotFoundException.class, () ->
                service.delete(RESTAURANT_RENAISSANCE.getId()));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.delete(1));
    }

    @Test
    void getAll() {
        List<Restaurant> all = service.getAll();
        RESTAURANT_MATCHERS.assertMatch(all, RESTAURANT_BURGER_KING, RESTAURANT_RENAISSANCE);
    }

    @Test
    void update() {
        Restaurant updated = getUpdated();
        service.update(new Restaurant(updated));
        RESTAURANT_MATCHERS.assertMatch(service.get(RESTAURANT_RENAISSANCE.getId()), updated);
    }

    @Test
    void createWithEmptyRestaurantName() {
        validateRootCause(() -> service.create(new Restaurant(null, "  ", "NewKitchen", "NewAddress", LocalDateTime.now())), ConstraintViolationException.class);
    }

    @Test
    void createWithEmptyRestaurantDescription() {
        Restaurant restaurant = service.create(new Restaurant(null, "NewRestaurant", "  ", "NewAddress", LocalDateTime.now()));

        Set<ConstraintViolation<Restaurant>> violations = validator.validate(restaurant, ErrorSequence.First.class);

        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> assertThat(action.getMessage()).isEqualTo("{restaurant.NotBlank.description}"));
    }

    @Test
    void createWithMinSizeRestaurantDescription() {
        Restaurant restaurant = service.create(new Restaurant(null, "NewRestaurant", "q", "NewAddress", LocalDateTime.now()));

        Set<ConstraintViolation<Restaurant>> violations = validator.validate(restaurant, ErrorSequence.Second.class);

        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> assertThat(action.getMessage()).isEqualTo("{restaurant.Size.description}"));
    }

    @Test
    void createWithMaxSizeRestaurantDescription() {
        Restaurant restaurant = service.create(new Restaurant(null, "NewRestaurant", "NewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNew", "NewAddress", LocalDateTime.now()));

        Set<ConstraintViolation<Restaurant>> violations = validator.validate(restaurant, ErrorSequence.Second.class);

        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> assertThat(action.getMessage()).isEqualTo("{restaurant.Size.description}"));
    }

    @Test
    void createWithEmptyRestaurantAddress() {
        Restaurant restaurant = service.create(new Restaurant(null, "NewRestaurant", "NewKitchen", "  ", LocalDateTime.now()));

        Set<ConstraintViolation<Restaurant>> violations = validator.validate(restaurant, ErrorSequence.First.class);

        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> assertThat(action.getMessage()).isEqualTo("{restaurant.NotBlank.address}"));
    }

    @Test
    void createWithMinSizeRestaurantAddress() {
        Restaurant restaurant = service.create(new Restaurant(null, "NewRestaurant", "NewKitchen", "q", LocalDateTime.now()));

        Set<ConstraintViolation<Restaurant>> violations = validator.validate(restaurant, ErrorSequence.Second.class);

        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> assertThat(action.getMessage()).isEqualTo("{restaurant.Size.address}"));
    }

    @Test
    void createWithMaxSizeRestaurantAddress() {
        Restaurant restaurant = service.create(new Restaurant(null, "NewRestaurant", "NewKitchen", "NewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNew", LocalDateTime.now()));

        Set<ConstraintViolation<Restaurant>> violations = validator.validate(restaurant, ErrorSequence.Second.class);

        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> assertThat(action.getMessage()).isEqualTo("{restaurant.Size.address}"));
    }
}