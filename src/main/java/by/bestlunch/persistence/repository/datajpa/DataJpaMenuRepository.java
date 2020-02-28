package by.bestlunch.persistence.repository.datajpa;

import by.bestlunch.persistence.model.Menu;
import by.bestlunch.persistence.repository.MenuRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static by.bestlunch.util.DateTimeUtil.getEndInclusive;

@Repository
public class DataJpaMenuRepository implements MenuRepository {

    private final CrudRestaurantRepository crudRestaurantRepository;
    private final CrudMenuRepository crudMenuRepository;

    public DataJpaMenuRepository(CrudRestaurantRepository restaurantRepository, CrudMenuRepository menuRepository) {
        this.crudRestaurantRepository = restaurantRepository;
        this.crudMenuRepository = menuRepository;
    }

    @Override
    public Menu save(Menu menuItem, int restaurantId) {
        if (!menuItem.isNew() && get(menuItem.getId(), restaurantId) == null) {
            return null;
        }
        menuItem.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudMenuRepository.save(menuItem);
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        return crudMenuRepository.delete(id, restaurantId) != 0;
    }

    @Override
    public Menu get(int id, int restaurantId) {
        return crudMenuRepository.findById(id)
                .filter(menu -> menu.getRestaurant().getId() == restaurantId).orElse(null);
    }

    @Override
    public List<Menu> getAll(int restaurantId) {
        return crudMenuRepository.getAll(restaurantId);
    }

    @Override
    public List<Menu> getAllByDate(int restaurantId, LocalDate date) {
        return crudMenuRepository.getAllByDate(restaurantId, getEndInclusive(date));
    }

    @Override
    public Menu getAllWithRestaurant(int id, int restaurantId) {
        return crudMenuRepository.getWithRestaurant(id, restaurantId);
    }
}