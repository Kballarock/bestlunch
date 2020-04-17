package by.bestlunch.web.controller.restaurant;

import by.bestlunch.persistence.model.Restaurant;
import by.bestlunch.persistence.model.Vote;
import by.bestlunch.web.dto.RestaurantDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = {
        RestaurantUserRestController.RESTAURANT_USER_REST_URL}, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantUserRestController extends AbstractRestaurantController {

    static final String RESTAURANT_USER_REST_URL = "/rest/restaurant";

    @Override
    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
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

    @PostMapping(value = "/{id}/vote", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createOrUpdateUserVoteToday(@PathVariable int id) {
        Vote newVote = super.createOrUpdateVote(id);

        URI uriOfNewVote = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RESTAURANT_USER_REST_URL + "/{id}/vote")
                .buildAndExpand(newVote.getId()).toUri();
        return ResponseEntity.created(uriOfNewVote).body(newVote);
    }

    @Override
    @DeleteMapping("{id}/vote")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteVote(@PathVariable int id) {
        super.deleteVote(id);
    }
}
