package by.bestlunch.service;

import by.bestlunch.persistence.model.Restaurant;
import by.bestlunch.persistence.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static by.bestlunch.validation.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantRepository.get(id), id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(int id) {
        checkNotFoundWithId(restaurantRepository.delete(id), id);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.getId());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }
}