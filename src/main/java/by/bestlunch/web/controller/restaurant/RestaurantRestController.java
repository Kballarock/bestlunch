package by.bestlunch.web.controller.restaurant;

import by.bestlunch.persistence.model.Restaurant;
import by.bestlunch.validation.view.View;
import by.bestlunch.web.dto.RestaurantDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantRestController.RESTAURANT_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController extends AbstractRestaurantController {

    static final String RESTAURANT_REST_URL = "/rest/admin";

    @Override
    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping
    public List<RestaurantDto> getAllWithCountPerDate() {
        return super.getAllWithCountPerDate();
    }

    @Override
    @GetMapping(value = "/filter")
    public List<RestaurantDto> getBetween(@RequestParam @Nullable LocalDate startDate,
                                          @RequestParam @Nullable LocalDate endDate) {
        return super.getBetween(startDate, endDate);
    }


    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody Restaurant restaurant, @PathVariable int id) {
        super.update(restaurant, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Validated(View.Web.class) @RequestBody Restaurant restaurant) {
        Restaurant created = super.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RESTAURANT_REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PostMapping(value = "/{id}/vote", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createOrUpdateVote(@PathVariable int id) {
        super.createOrUpdateVote(id);
    }

    @Override
    @DeleteMapping("{id}/vote")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteVote(@PathVariable int id) {
        super.deleteVote(id);
    }
}
