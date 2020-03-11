package by.bestlunch.web.controller.menu;

import by.bestlunch.persistence.model.Menu;
import by.bestlunch.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static by.bestlunch.validation.ValidationUtil.assureIdConsistent;
import static by.bestlunch.validation.ValidationUtil.checkNew;

public abstract class AbstractMenuController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuService menuService;

    public Menu get(int id, int restaurantId) {
        log.info("get menu item {} for restaurant {}", id, restaurantId);
        return menuService.get(id, restaurantId);
    }

    public void delete(int id, int restaurantId) {
        log.info("delete menu item {} for restaurant {}", id, restaurantId);
        menuService.delete(id, restaurantId);
    }

    List<Menu> getAllByDate(int restaurantId, LocalDate date) {
        log.info("getAllByDate per date {}, for restaurant {}", date, restaurantId);
        return menuService.getAllByDate(restaurantId, date);
    }

    public Menu create(Menu item, int restaurantId) {
        checkNew(item);
        log.info("create menu item {} for restaurant {}", item, restaurantId);
        return menuService.create(item, restaurantId);
    }

    public void update(Menu item, int id, int restaurantId) {
        assureIdConsistent(item, id);
        log.info("update menu item {} with restaurantId={}", item, restaurantId);
        menuService.update(item, restaurantId);
    }
}