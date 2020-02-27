package by.bestlunch.service;

import by.bestlunch.persistence.model.Menu;
import by.bestlunch.persistence.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MenuService {

    private final MenuRepository repository;

    @Autowired
    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }

    public List<Menu> getAllbyDate(Integer restaurantId, LocalDate date) {
        return repository.findAllByDate(restaurantId, date);
    }
}