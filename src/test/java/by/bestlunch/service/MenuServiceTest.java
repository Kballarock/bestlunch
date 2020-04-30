package by.bestlunch.service;

import by.bestlunch.persistence.model.Menu;
import by.bestlunch.util.exception.ErrorType;
import by.bestlunch.util.exception.NotFoundException;
import by.bestlunch.validation.view.ErrorSequence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static by.bestlunch.MenuTestData.*;
import static by.bestlunch.RestaurantTestData.RESTAURANT_BURGER_KING;
import static by.bestlunch.RestaurantTestData.RESTAURANT_RENAISSANCE;
import static java.time.LocalDate.of;
import static org.junit.jupiter.api.Assertions.*;

class MenuServiceTest extends AbstractServiceTest {

    @Autowired
    private MenuService service;
    private static Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void get() {
        Menu actual = service.get(RESTAURANT_BURGER_KING.getId(), MENU_ITEM1.getId());
        MENU_MATCHERS.assertMatch(actual, MENU_ITEM1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.get(1, RESTAURANT_BURGER_KING.getId()));
    }

    @Test
    void getNotOwn() {
        assertThrows(NotFoundException.class, () ->
                service.get(MENU_ITEM3.getId(), RESTAURANT_BURGER_KING.getId()));
    }

    @Test
    void delete() {
        service.delete(MENU_ITEM3.getId(), RESTAURANT_RENAISSANCE.getId());
        assertThrows(NotFoundException.class, () ->
                service.get(MENU_ITEM3.getId(), RESTAURANT_RENAISSANCE.getId()));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.delete(1, RESTAURANT_RENAISSANCE.getId()));
    }

    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class, () ->
                service.delete(MENU_ITEM4.getId(), RESTAURANT_BURGER_KING.getId()));
    }

    @Test
    void getAllByDate() {
        List<Menu> menuList = service.getAllByDate(RESTAURANT_RENAISSANCE.getId(), of(2019, 12, 16));
        MENU_MATCHERS.assertMatch(menuList, MENU_ITEMS);
    }

    @Test
    void update() {
        Menu updated = getUpdated();
        service.update(updated, RESTAURANT_BURGER_KING.getId());
        MENU_MATCHERS.assertMatch(service.get(MENU_ITEM5.getId(), RESTAURANT_BURGER_KING.getId()), updated);
    }

    @Test
    void updateNotFound() {
        NotFoundException e = assertThrows(NotFoundException.class, () -> service.update(MENU_ITEM3, RESTAURANT_BURGER_KING.getId()));
        String msg = e.getMessage();
        assertTrue(msg.contains(ErrorType.DATA_NOT_FOUND.name()));
        assertTrue(msg.contains(NotFoundException.NOT_FOUND_EXCEPTION));
        assertTrue(msg.contains(String.valueOf(MENU_ITEM3.getId())));
    }

    @Test
    void create() {
        Menu newItem = getNew();
        Menu created = service.create(newItem, RESTAURANT_BURGER_KING.getId());
        Integer newId = created.getId();
        newItem.setId(newId);
        MENU_MATCHERS.assertMatch(created, newItem);
        MENU_MATCHERS.assertMatch(service.get(newId, RESTAURANT_BURGER_KING.getId()), newItem);
    }

    @Test
    void createWithEmptyMenuItemName() {
        Menu item = service.create(new Menu(null, "  ", 3.15, LocalDate.now()), RESTAURANT_BURGER_KING.getId());

        Set<ConstraintViolation<Menu>> violations = validator.validate(item, ErrorSequence.First.class);

        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> assertThat(action.getMessage()).isEqualTo("{menu.NotBlank.name}"));
    }

    @Test
    void createWithMinSizeMenuItemName() {
        Menu item = service.create(new Menu(null, "q", 3.15, LocalDate.now()), RESTAURANT_BURGER_KING.getId());

        Set<ConstraintViolation<Menu>> violations = validator.validate(item, ErrorSequence.Second.class);

        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> assertThat(action.getMessage()).isEqualTo("{menu.Size.name}"));
    }

    @Test
    void createWithMaxSizeMenuItemName() {
        Menu item = service.create(new Menu(null, "NewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNewNew", 3.15, LocalDate.now()), RESTAURANT_BURGER_KING.getId());

        Set<ConstraintViolation<Menu>> violations = validator.validate(item, ErrorSequence.Second.class);

        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> assertThat(action.getMessage()).isEqualTo("{menu.Size.name}"));
    }

    @Test
    void createWithMinMenuItemPrice() {
        Menu item = service.create(new Menu(null, "NewItem", -1.00, LocalDate.now()), RESTAURANT_BURGER_KING.getId());

        Set<ConstraintViolation<Menu>> violations = validator.validate(item, ErrorSequence.Second.class);

        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> assertThat(action.getMessage()).isEqualTo("{menu.DecimalMin.price}"));
    }

    @Test
    void createWithMaxMenuItemPrice() {
        Menu item = service.create(new Menu(null, "NewItem", 10000.0, LocalDate.now()), RESTAURANT_BURGER_KING.getId());

        Set<ConstraintViolation<Menu>> violations = validator.validate(item, ErrorSequence.Second.class);

        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> assertThat(action.getMessage()).isEqualTo("{menu.DecimalMax.price}"));
    }
}