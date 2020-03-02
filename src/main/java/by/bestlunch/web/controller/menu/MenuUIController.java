package by.bestlunch.web.controller.menu;

import by.bestlunch.persistence.model.Menu;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import static by.bestlunch.util.DateTimeUtil.getCurrentDate;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ajax/admin/{restaurantId}/menu")
public class MenuUIController extends AbstractMenuController {

    @Override
    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Menu> getAllByDate(@PathVariable("restaurantId") int restaurantId, @RequestParam @Nullable LocalDate date) {
        return super.getAllByDate(restaurantId, getCurrentDate(date));
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable("id") int id, @PathVariable("restaurantId") int restaurantId) {
        return super.get(id, restaurantId);
    }
}