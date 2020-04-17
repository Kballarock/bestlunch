package by.bestlunch.web.controller.restaurant;

import by.bestlunch.persistence.model.Restaurant;
import by.bestlunch.validation.view.ErrorSequence;
import by.bestlunch.web.dto.RestaurantDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = {"/ajax/admin", "/ajax/restaurant"})
public class RestaurantUIController extends AbstractRestaurantController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantDto> getAllWithCountPerDate() {
        return super.getAllWithCountPerDate();
    }

    @Override
    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantDto> getBetween(@RequestParam @Nullable LocalDate startDate,
                                          @RequestParam @Nullable LocalDate endDate) {
        return super.getBetween(startDate, endDate);
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@Validated(ErrorSequence.class) Restaurant restaurant) {
        if (restaurant.isNew()) {
            super.create(restaurant);
        } else {
            super.update(restaurant, restaurant.id());
        }
    }

    @PostMapping("/{id}/vote")
    public void createOrUpdateUserVoteToday(@PathVariable int id) {
        super.createOrUpdateVote(id);
    }

    @Override
    @DeleteMapping("{id}/vote")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteVote(@PathVariable int id) {
        super.deleteVote(id);
    }
}