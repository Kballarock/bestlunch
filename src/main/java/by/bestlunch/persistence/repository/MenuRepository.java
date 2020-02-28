package by.bestlunch.persistence.repository;

import by.bestlunch.persistence.model.Menu;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepository {

    Menu save(Menu menuItem, int restaurantId);

    boolean delete(int id, int restaurantId);

    Menu get(int id, int userId);

    List<Menu> getAll(int restaurantId);

    List<Menu> getAllByDate(int restaurantId, @Nullable LocalDate date);

    default Menu getAllWithRestaurant(int id, int restaurantId) {
        throw new UnsupportedOperationException();
    }
}