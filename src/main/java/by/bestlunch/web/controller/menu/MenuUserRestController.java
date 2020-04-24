package by.bestlunch.web.controller.menu;

import by.bestlunch.persistence.model.Menu;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static by.bestlunch.util.DateTimeUtil.getCurrentDate;

@RestController
@RequestMapping(value = "/rest/restaurant/{restaurantId}/menu", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuUserRestController extends AbstractMenuController {

    @Override
    @GetMapping
    List<Menu> getAllByDate(@PathVariable("restaurantId") int restaurantId, @RequestParam @Nullable LocalDate date) {
        return super.getAllByDate(restaurantId, getCurrentDate(date));
    }
}