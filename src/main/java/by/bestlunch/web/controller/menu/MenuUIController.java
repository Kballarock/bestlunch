package by.bestlunch.web.controller.menu;

import by.bestlunch.persistence.model.Menu;
import by.bestlunch.validation.view.ErrorSequence;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static by.bestlunch.util.DateTimeUtil.getCurrentDate;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ajax/{restaurantId}/menu")
public class MenuUIController extends AbstractMenuController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Menu> getAllByDate(@PathVariable("restaurantId") int restaurantId, @RequestParam @Nullable LocalDate date) {
        return super.getAllByDate(restaurantId, getCurrentDate(date));
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable("id") int id, @PathVariable("restaurantId") int restaurantId) {
        return super.get(id, restaurantId);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id, @PathVariable("restaurantId") int restaurantId) {
        super.delete(id, restaurantId);
    }

    @PostMapping
    public void createOrUpdate(@Validated(ErrorSequence.class) Menu item, @PathVariable("restaurantId") int restaurantId) {
        if (item.isNew()) {
            super.create(item, restaurantId);
        } else {
            super.update(item, item.id(), restaurantId);
        }
    }
}