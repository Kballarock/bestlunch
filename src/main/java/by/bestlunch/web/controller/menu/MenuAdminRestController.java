package by.bestlunch.web.controller.menu;

import by.bestlunch.persistence.model.Menu;
import by.bestlunch.validation.view.ErrorSequence;
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

import static by.bestlunch.util.DateTimeUtil.getCurrentDate;

@RestController
@RequestMapping(value = "/rest/admin/restaurant/{restaurantId}/menu/items", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuAdminRestController extends AbstractMenuController {

    @Override
    @GetMapping
    List<Menu> getAllByDate(@PathVariable("restaurantId") int restaurantId, @RequestParam @Nullable LocalDate date) {
        return super.getAllByDate(restaurantId, getCurrentDate(date));
    }

    @Override
    @GetMapping(value = "/{id}")
    public Menu get(@PathVariable("id") int id, @PathVariable("restaurantId") int restaurantId) {
        return super.get(id, restaurantId);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id, @PathVariable("restaurantId") int restaurantId) {
        super.delete(id, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@Validated(ErrorSequence.class) @RequestBody Menu item,
                                                   @PathVariable("restaurantId") int restaurantId) {
        Menu created = super.create(item, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/restaurant/{restaurantId}/menu/items")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(ErrorSequence.class) @RequestBody Menu item,
                       @PathVariable("id") int id, @PathVariable("restaurantId") int restaurantId) {
        super.update(item, id, restaurantId);
    }
}