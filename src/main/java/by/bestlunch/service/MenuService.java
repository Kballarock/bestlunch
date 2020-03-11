package by.bestlunch.service;

import by.bestlunch.persistence.model.Menu;
import by.bestlunch.persistence.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static by.bestlunch.validation.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuService {

    private final MenuRepository repository;

    @Autowired
    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }

    public Menu get(int id, int restaurantId) {
        return checkNotFoundWithId(repository.get(id, restaurantId), id);
    }

    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(repository.delete(id, restaurantId), id);
    }

    public List<Menu> getAllByDate(int restaurantId, @Nullable LocalDate date) {
        return repository.getAllByDate(restaurantId, date);
    }

    @Transactional
    public void update(Menu item, int restaurantId) {
        Assert.notNull(item, "menu must not be null");
        checkNotFoundWithId(repository.save(item, restaurantId), item.getId());
    }

    @Transactional
    public Menu create(Menu item, int restaurantId) {
        Assert.notNull(item, "menu must not be null");
        return repository.save(item, restaurantId);
    }
}